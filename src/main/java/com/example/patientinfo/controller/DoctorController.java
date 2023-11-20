package com.example.patientinfo.controller;

import com.example.patientinfo.data.dto.*;
import com.example.patientinfo.data.entity.Doctor;
import com.example.patientinfo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/V2/API")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping(value = "/findNumber")
    public ResponseEntity<DoctorResponseDto> getDoctor(Long number) {
        DoctorResponseDto doctorResponseDto = doctorService.getDoctor(number);

        return ResponseEntity.status(HttpStatus.OK).body(doctorResponseDto);
    }

    @GetMapping(value = "/findName")
    public List<Doctor> getDoctorName(String name) {
        return doctorService.getDoctorName(name);
    }

    @GetMapping(value = "/findAll")
    public Page<Doctor> getPatientAll(@RequestParam(value = "page", defaultValue = "0") int page) {
        return doctorService.getDoctorAll(page);
    }

    @PostMapping()
    public ResponseEntity<DoctorResponseDto> saveDoctor(@RequestBody @Valid DoctorDto doctorDto) {
        DoctorResponseDto doctorResponseDto = doctorService.saveDoctor(doctorDto);

        return ResponseEntity.status(HttpStatus.OK).body(doctorResponseDto);
    }

    @PutMapping()
    public ResponseEntity<DoctorResponseDto> changeDoctor(@RequestBody ChangedDoctorDto changedDoctorDto) throws Exception {
        DoctorResponseDto doctorResponseDto = doctorService.changeDoctor(
                changedDoctorDto.getNumber(),
                changedDoctorDto.getPwd(),
                changedDoctorDto.getTel_no_1(),
                changedDoctorDto.getTel_no_2(),
                changedDoctorDto.getTel_no_3());

        return ResponseEntity.status(HttpStatus.OK).body(doctorResponseDto);

    }

    @DeleteMapping()
    public ResponseEntity<String> deleteDoctor(Long number) throws Exception {
        doctorService.deleteDoctor(number);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
}
