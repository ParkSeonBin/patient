package com.example.patientinfo.service;

import com.example.patientinfo.data.dto.DoctorDto;
import com.example.patientinfo.data.dto.PatientDto;
import com.example.patientinfo.data.dto.SignInResultDto;
import com.example.patientinfo.data.dto.SignUpResultDto;

public interface SignService {
    SignUpResultDto patientSignUp(PatientDto patientDto) throws Exception;
    SignUpResultDto doctorSignUp(DoctorDto doctorDto) throws Exception;
    SignInResultDto patientSignIn(String id, String password) throws RuntimeException;
    SignInResultDto doctorSignIn(String id, String password) throws RuntimeException;
    void logout() throws Exception;

}
