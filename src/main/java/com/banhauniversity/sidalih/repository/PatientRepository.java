package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.dto.StudentPerCollageDTO;
import com.banhauniversity.sidalih.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

    List<Patient> findAllByChronic(boolean chronic);

    List<Patient> findAllByCollegeName(String collegeName);

    @Query("select count(p.name) from Patient as p")
    int studentCount();

    @Query("select new com.banhauniversity.sidalih.dto.StudentPerCollageDTO(p.collegeName,COUNT(p.nationalid)) from Patient as p GROUP BY p.collegeName")
    List<StudentPerCollageDTO> getStudentperCollage();
}
