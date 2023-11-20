package com.example.patientinfo.service.impl;

import com.example.patientinfo.service.PatientService;
import com.example.patientinfo.data.dto.PatientDto;
import com.example.patientinfo.data.dto.PatientResponseDto;
import com.example.patientinfo.data.entity.Patient;
import com.example.patientinfo.data.repository.PatientRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final Logger LOGGER = LoggerFactory.getLogger(PatientServiceImpl.class);
    private final PatientRepository patientRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public PatientServiceImpl(PatientRepository productRepository) {
        this.patientRepository = productRepository;
    }

    @Override
    public PatientResponseDto getPatient(Long number) {
        LOGGER.info("[getProduct] input number : {}", number);
        Patient product = patientRepository.findById(number).get();

        PatientResponseDto patientResponseDto = modelMapper.map(product, PatientResponseDto.class);
//        PatientResponseDto patientResponseDto = new PatientResponseDto();
//        patientResponseDto.setNumber(product.getNumber());
//        patientResponseDto.setBirth(product.getBirth());
//        patientResponseDto.setEmail(product.getEmail());
//        patientResponseDto.setGender(String.valueOf(product.getGender()));
//        patientResponseDto.setName(product.getName());
//        patientResponseDto.setTel_no_1(product.getTel_no_1());
//        patientResponseDto.setTel_no_2(product.getTel_no_2());
//        patientResponseDto.setTel_no_3(product.getTel_no_3());
//        patientResponseDto.setCreatedAt(product.getCreatedAt());
//        patientResponseDto.setUpdatedAt(product.getUpdatedAt());

        return patientResponseDto;
    }

    @Override
    public List<Patient> getPatientName(String name) {
        return patientRepository.findByName(name);
    }

    @Override
    public Page<Patient> getPatientAll(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return patientRepository.findAll(pageable);
    }

    @Override
    public PatientResponseDto savePatient(PatientDto patientDto) {
        LOGGER.info("[saveProduct] productDTO : {}", patientDto.toString());
        Patient patient = modelMapper.map(patientDto, Patient.class);
//        Patient patient = new Patient();
//        patient.setBirth(patientDto.getBirth());
//        patient.setEmail(patientDto.getEmail());
//        patient.setGender(Gender.valueOf(patientDto.getGender()));
//        patient.setName(patientDto.getName());
//        patient.setTel_no_1(patientDto.getTel_no_1());
//        patient.setTel_no_2(patientDto.getTel_no_2());
//        patient.setTel_no_3(patientDto.getTel_no_3());
//        patient.setUpdatedAt(LocalDateTime.now());
//        patient.setCreatedAt(LocalDateTime.now());

        Patient savedPatient = patientRepository.save(patient);
        LOGGER.info("[save] savedPatient : {}", savedPatient);

        PatientResponseDto patientResponseDto = modelMapper.map(savedPatient, PatientResponseDto.class);
//        PatientResponseDto patientResponseDto = new PatientResponseDto();
//        patientResponseDto.setNumber(savedPatient.getNumber());
//        patientResponseDto.setBirth(savedPatient.getBirth());
//        patientResponseDto.setEmail(savedPatient.getEmail());
//        Gender gender = savedPatient.getGender();
//        patientResponseDto.setGender(String.valueOf(gender));
//        patientResponseDto.setName(savedPatient.getName());
//        patientResponseDto.setTel_no_1(savedPatient.getTel_no_1());
//        patientResponseDto.setTel_no_2(savedPatient.getTel_no_2());
//        patientResponseDto.setTel_no_3(savedPatient.getTel_no_3());
//        patientResponseDto.setUpdatedAt(LocalDateTime.now());
//        patientResponseDto.setCreatedAt(LocalDateTime.now());

        return patientResponseDto;
    }

    @Override
    public PatientResponseDto changePatient(Long number, String email, String tel_no_1, String tel_no_2, String tel_no_3) {
        Patient foundProduct = patientRepository.findById(number).get();
        foundProduct.setEmail(email);
        foundProduct.setTel_no_1(tel_no_1);
        foundProduct.setTel_no_2(tel_no_2);
        foundProduct.setTel_no_3(tel_no_3);
        //수정시간 안바뀌던거 이거 추가하고 나서 성공
        foundProduct.setUpdatedAt(LocalDateTime.now());

        Patient changedPatient = patientRepository.save(foundProduct);

        PatientResponseDto patientResponseDto = modelMapper.map(changedPatient, PatientResponseDto.class);
//        PatientResponseDto patientResponseDto = new PatientResponseDto();
//        patientResponseDto.setNumber(changedPatient.getNumber());
//        patientResponseDto.setBirth(foundProduct.getBirth());
        patientResponseDto.setEmail(changedPatient.getEmail());
//        patientResponseDto.setGender(String.valueOf(foundProduct.getGender()));
//        patientResponseDto.setName(foundProduct.getName());
        patientResponseDto.setTel_no_1(changedPatient.getTel_no_1());
        patientResponseDto.setTel_no_2(changedPatient.getTel_no_2());
        patientResponseDto.setTel_no_3(changedPatient.getTel_no_3());
//        patientResponseDto.setCreatedAt(changedPatient.getCreatedAt());
//        patientResponseDto.setUpdatedAt(changedPatient.getUpdatedAt());

        return patientResponseDto;
    }

    @Override
    public void deletePatient(Long number) {
        patientRepository.deleteById(number);
    }
}