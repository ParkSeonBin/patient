package com.example.patientinfo.service;

import com.example.patientinfo.data.dto.DoctorDto;
import com.example.patientinfo.data.dto.DoctorResponseDto;
import com.example.patientinfo.data.entity.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DoctorService {
    DoctorResponseDto getDoctor(Long number);

    List<Doctor> getDoctorName(String name);

    Page<Doctor> getDoctorAll(int page);

    DoctorResponseDto saveDoctor(DoctorDto doctorDto);

    DoctorResponseDto changeDoctor(Long number, String pwd, String tel_no_1, String tel_no_2, String tel_no_3) throws Exception;

    void deleteDoctor(Long number) throws Exception;

}
