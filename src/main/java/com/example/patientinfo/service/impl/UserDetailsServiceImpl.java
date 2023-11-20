package com.example.patientinfo.service.impl;

import com.example.patientinfo.data.entity.Doctor;
import com.example.patientinfo.data.entity.Patient;
import com.example.patientinfo.data.repository.DoctorRepository;
import com.example.patientinfo.data.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.patientinfo.service.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;


    @Override
    public UserDetails loadPatientByUsername(String name) throws UsernameNotFoundException {
        Patient patient = patientRepository.findByEmail(name);

        return new User(patient.getEmail(), patient.getPwd(), new ArrayList<>());
    }

    @Override
    public UserDetails loadDoctorByUsername(String name) throws UsernameNotFoundException {
        Doctor doctor = doctorRepository.findByEmail(name);

        return new User(doctor.getEmail(), doctor.getPwd(), new ArrayList<>());
    }
}
