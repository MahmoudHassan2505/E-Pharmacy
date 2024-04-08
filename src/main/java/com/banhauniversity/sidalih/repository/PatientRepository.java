package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

    List<Patient> findAllByChronic(boolean chronic);

    List<Patient> findAllByCollegeName(String collegeName);
}
