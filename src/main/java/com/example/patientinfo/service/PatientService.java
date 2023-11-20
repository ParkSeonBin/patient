package com.example.patientinfo.service;

import com.example.patientinfo.data.dto.PatientDto;
import com.example.patientinfo.data.dto.PatientResponseDto;
import com.example.patientinfo.data.entity.Patient;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientService {

    PatientResponseDto getPatient(Long number);

    List<Patient> getPatientName(String name);

    Page<Patient> getPatientAll(int page);

    PatientResponseDto savePatient(PatientDto patientDto);

    PatientResponseDto changePatient(Long number, String email, String tel_no_1, String tel_no_2, String tel_no_3) throws Exception;

    void deletePatient(Long number) throws Exception;

}