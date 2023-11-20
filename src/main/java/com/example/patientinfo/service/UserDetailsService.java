package com.example.patientinfo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadPatientByUsername(String name) throws UsernameNotFoundException;
    UserDetails loadDoctorByUsername(String name) throws UsernameNotFoundException;
}
