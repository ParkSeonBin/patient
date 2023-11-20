package com.example.patientinfo.data.repository;

import com.example.patientinfo.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByName(String name);

    Doctor findByEmail(String email);
}
