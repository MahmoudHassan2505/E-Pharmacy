package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.entity.Medicine;

import static org.assertj.core.api.Assertions.assertThat;

import com.banhauniversity.sidalih.entity.MedicineCategory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;


@DataJpaTest
class MedicineRepositoryTest {

    @Autowired MedicineRepository medicineRepository;
    @Autowired MedicineCategoryRepository medicineCategoryRepository;

    Medicine panadol;
    Medicine ogmenten;
    Medicine drug;
    Medicine inj;

    MedicineCategory syrup;
    MedicineCategory drugs;
    MedicineCategory injections;

    @BeforeEach
    void arrange(){
        //arrange

        syrup = MedicineCategory.builder()
                .name("شراب")
                .build();

        drugs = MedicineCategory.builder()
                .name("كبسول")
                .build();

        injections = MedicineCategory.builder()
                .name("حقنه")
                .build();

        medicineCategoryRepository.save(syrup);
        medicineCategoryRepository.save(drugs);
        medicineCategoryRepository.save(injections);

        panadol = Medicine.builder()
                .barcode(1)
                .activeingredient("Headache")
                .medicineCategory(drugs)
                .alertamount(20)
                .alertexpired(10)
                .name("Panadol Extra")
                .arabicname("بنادول")
                .unit("شريط")
                .strength("200Mg")
                .dosageform("اقراص")
                .build();

        drug = Medicine.builder()
                .barcode(2)
                .activeingredient("Headache")
                .medicineCategory(drugs)
                .alertamount(20)
                .alertexpired(10)
                .name("Extra Extra")
                .arabicname("بنادول")
                .unit("شريط")
                .strength("200Mg")
                .dosageform("اقراص")
                .build();

        ogmenten = Medicine.builder()
                .barcode(3)
                .activeingredient("stomach")
                .medicineCategory(syrup)
                .alertamount(20)
                .alertexpired(10)
                .name("Ogmentine Extra")
                .arabicname("توجمنتين")
                .unit("شراب")
                .strength("500mg")
                .dosageform("شراب")
                .build();

        inj = Medicine.builder()
                .barcode(4)
                .activeingredient("stomach")
                .medicineCategory(injections)
                .alertamount(20)
                .alertexpired(10)
                .name("Injectors")
                .arabicname("توجمنتين")
                .unit("شراب")
                .strength("500mg")
                .dosageform("حقنه")
                .build();

        medicineRepository.save(panadol);
        medicineRepository.save(ogmenten);
        medicineRepository.save(drug);
        medicineRepository.save(inj);

    }
    @Test
    void findMix() {
        //Act
        List<Medicine> medicineList = medicineRepository.findMix();

        //Assert
        assertThat(medicineList.size()).isEqualTo(4);
    }

    @Test
    void findAllByMedicineCategoryId() {
        //Act
        List<Medicine> medicineList = medicineRepository.findAllByMedicineCategoryId(2);
        assertThat(medicineList.size()).isEqualTo(2);
    }

    @Test
    void findByBarcode() {
        //Act
        Medicine medicine = medicineRepository.findByBarcode(1).get();

        //Assertion
        assertThat(medicine.getName()).isEqualTo(panadol.getName());
        assertThat(medicine.getAlertamount()).isEqualTo(panadol.getAlertamount());
    }


}