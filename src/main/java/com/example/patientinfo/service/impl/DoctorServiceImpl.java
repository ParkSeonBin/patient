package com.example.patientinfo.service.impl;

import com.example.patientinfo.data.dto.DoctorDto;
import com.example.patientinfo.data.dto.DoctorResponseDto;
import com.example.patientinfo.data.entity.Doctor;
import com.example.patientinfo.data.repository.DoctorRepository;
import com.example.patientinfo.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final Logger LOGGER = LoggerFactory.getLogger(DoctorServiceImpl.class);
    private final DoctorRepository doctorRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public DoctorResponseDto getDoctor(Long number) {

        LOGGER.info("[getDoctor] input number : {}", number);
        Doctor doctor = doctorRepository.findById(number).get();

        //findById로 찾은 대상 == Doctor Entity
        //반환 대상 == DoctorResponseDto
        DoctorResponseDto doctorResponseDto = modelMapper.map(doctor, DoctorResponseDto.class);

        return doctorResponseDto;
    }

    @Override
    public List<Doctor> getDoctorName(String name) { return doctorRepository.findByName(name);}

    @Override
    public Page<Doctor> getDoctorAll(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return doctorRepository.findAll(pageable);
    }

    @Override
    public DoctorResponseDto saveDoctor(DoctorDto doctorDto) {
        LOGGER.info("[saveDoctor] productDTO : {}", doctorDto.toString());

        //파라미터로 받아온 대상 == DoctorDto
        //반환 대상 == Doctor
        Doctor doctor = modelMapper.map(doctorDto, Doctor.class);

        Doctor savedDoctor = doctorRepository.save(doctor);
        LOGGER.info("[save] savedDoctor : {}", savedDoctor);

        //save 되어있는 대상 == Doctor
        //반환 대상 == DoctorResponseDto
        DoctorResponseDto doctorResponseDto = modelMapper.map(savedDoctor, DoctorResponseDto.class);

        return doctorResponseDto;
    }

    @Override
    public DoctorResponseDto changeDoctor(Long number, String pwd, String tel_no_1, String tel_no_2, String tel_no_3) throws Exception {
        Doctor foundDoctor = doctorRepository.findById(number).get();
        foundDoctor.setPwd(pwd);
        foundDoctor.setTel_no_1(tel_no_1);
        foundDoctor.setTel_no_2(tel_no_2);
        foundDoctor.setTel_no_3(tel_no_3);
        //수정시간 안바뀌던거 이거 추가하고 나서 성공
        //foundDoctor.setUpdatedAt(LocalDateTime.now());

        Doctor changedDoctor = doctorRepository.save(foundDoctor);

        //findById로 찾은 대상 == Doctor Entity
        //반환 대상 == DoctorDto
        DoctorResponseDto doctorResponseDto = modelMapper.map(changedDoctor, DoctorResponseDto.class);
        doctorResponseDto.setPwd(changedDoctor.getPwd());
        doctorResponseDto.setTel_no_1(changedDoctor.getTel_no_1());
        doctorResponseDto.setTel_no_2(changedDoctor.getTel_no_2());
        doctorResponseDto.setTel_no_3(changedDoctor.getTel_no_3());

        return doctorResponseDto;
    }

    @Override
    public void deleteDoctor(Long number) throws Exception { doctorRepository.deleteById(number); }
}
