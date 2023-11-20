package com.example.patientinfo.data.repository;

import com.example.patientinfo.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 예제 6.7
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByName(String name);
    Patient findByEmail(String email);
}
