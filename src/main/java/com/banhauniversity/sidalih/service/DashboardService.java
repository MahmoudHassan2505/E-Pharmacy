package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.dto.Reports;
import com.banhauniversity.sidalih.dto.SalesDTO;
import com.banhauniversity.sidalih.dto.StatisticsDTO;
import com.banhauniversity.sidalih.dto.StudentPerCollageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired PatientServices patientServices;
    @Autowired UseageService useageService;
    @Autowired NotificationService notificationService;

    public StatisticsDTO getStatistics(int year) {
    int studentCount = patientServices.getStudentCount();
    int prescriptionCount = useageService.getUseagePrescriptionCount(year);
    int runningOut = notificationService.getRunningOut();
    int aboutToExpire = notificationService.getAboutToExpire();
    List<StudentPerCollageDTO> studentPerCollages = patientServices.getStudentperCollage();
    List<Reports> prescriptionPerCollage = useageService.getPrescriptionPerCollage(year);
    List<SalesDTO> salesDTOList= useageService.getSalesStatistics(year);

    StatisticsDTO statistics = StatisticsDTO.builder()
            .studentCount(studentCount)
            .prescriptionCount(prescriptionCount)
            .runningOut(runningOut)
            .aboutToExpire(aboutToExpire)
            .studentPerCollage(studentPerCollages)
            .prescriptionPerCollage(prescriptionPerCollage)
            .sales(salesDTOList)
            .build();

    return statistics;
    }
}
