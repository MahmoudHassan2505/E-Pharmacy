package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.Medicine;
import com.banhauniversity.sidalih.entity.Notification;
import com.banhauniversity.sidalih.entity.Useage;
import com.banhauniversity.sidalih.entity.UseageMedicine;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.NotificationRepository;
import com.banhauniversity.sidalih.repository.UseageMedicineRepository;
import com.banhauniversity.sidalih.repository.UseageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class UseageService {

    @Autowired private UseageRepository useageRepository;
    @Autowired private UseageMedicineRepository useageMedicineRepository;
    @Autowired private InventoryService inventoryService;
    @Autowired private NotificationRepository notificationRepository;

    public List<Useage> findAll(){
        return useageRepository.findAll();
    }

    public Useage findById(long id){
        return useageRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
    }

    public Useage add(Useage useage){
        useageRepository.findById(useage.getId()).ifPresent((a)->{
            throw new CustomException(ExceptionMessage.ID_is_Exist);
        });

        boolean isChronic = useage.getPrescription().getPrsPrescriptionCategory().getId()==1;

        ValidateMedicine(useage);
        ValidatePrescription(useage);
        ValidatePrescriptionTimes(useage,isChronic);

        Useage savedUseage = useageRepository.saveAndFlush(useage);

        useage.getUseageMedicines().forEach(useageMedicine -> {
            useageMedicine.setUseage(savedUseage);
            useageMedicineRepository.save(useageMedicine);
            updateInventory(useageMedicine.getMedicine(),useageMedicine.getAmount());

        });

        updateNotifications(savedUseage.getId());

        return useageRepository.findById(savedUseage.getId()).orElseThrow(()-> new CustomException(ExceptionMessage.ID_Not_Found));
    }

    public Useage update(Useage useage){
        useageRepository.findById(useage.getId()).ifPresent((x)-> new CustomException(ExceptionMessage.ID_Not_Found));
        return useageRepository.save(useage);
    }

    public boolean delete(long id){
        useageRepository.deleteById(id);
        return true;
    }


    private void ValidateMedicine(Useage useage) {
    useage.getUseageMedicines().forEach((medicine)-> {
        if (medicine.getAmount() > inventoryService.Status(medicine.getMedicine().getId()).getAmount()) {
            throw new CustomException(ExceptionMessage.Not_Enough_Amount);
        }
    });

    }

    private void ValidatePrescription(Useage useage) {
        long total=0;

        for (UseageMedicine medicine:useage.getUseageMedicines()) {
            total+=medicine.getAmount()*medicine.getPrice();
        }
        if(total>350){
            throw new CustomException(ExceptionMessage.Patient_Exceeded_Price_Limit);
        }
    }

    private void updateInventory(Medicine medicine, long amount) {
        inventoryService.update(medicine.getId(),amount);
    }

    private boolean ValidatePrescriptionTimes(Useage useage,boolean isChronic) {
        String currentDate = String.valueOf(java.time.LocalDate.now().getYear());
        currentDate+="-";
        currentDate+=String.valueOf(java.time.LocalDate.now().getMonthValue());

        if(useageRepository.useageTimes(useage.getPrescription().getId(),currentDate)==0 &&isChronic){
            return true;
        }
        if (!isChronic&&useageRepository.useageTimes(useage.getPrescription().getId(),currentDate)<2) {
            return true;
        }
        throw new CustomException(ExceptionMessage.Patient_Exceeded_Useage_Limit);
    }

    public List<Useage> findByMedicineName(String medicineName) {
        return useageRepository.findAllByUseageMedicinesMedicineName(medicineName);
    }

    private void updateNotifications(Long id) {
        Useage useage = useageRepository.findById(id).orElseThrow(()-> new CustomException(ExceptionMessage.ID_Not_Found));
        System.out.println("hello3");
        useage.getUseageMedicines().forEach((medicine)->{
            if (medicine.getMedicine().getAlertamount() >= inventoryService.Status(medicine.getMedicine().getId()).getAmount()){
                notificationRepository.save(new Notification("اوشك الدواء علي النفاد",medicine.getMedicine()));
            }
        });
    }
}