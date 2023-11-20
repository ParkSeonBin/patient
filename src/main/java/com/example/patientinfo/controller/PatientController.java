package com.example.patientinfo.controller;

import com.example.patientinfo.data.dto.ChangePatientDto;
import com.example.patientinfo.data.dto.PatientDto;
import com.example.patientinfo.data.dto.PatientResponseDto;
import com.example.patientinfo.data.entity.Patient;
import com.example.patientinfo.service.PatientService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/V1/API")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService productService) {
        this.patientService = productService;
    }

    @GetMapping(value = "/findNumber")
    public ResponseEntity<PatientResponseDto> getPatient(Long number) {
        PatientResponseDto patientResponseDto = patientService.getPatient(number);

        return ResponseEntity.status(HttpStatus.OK).body(patientResponseDto);
    }

    @GetMapping(value = "/findName")
    public List<Patient> getPatientByName(String name) {
        return patientService.getPatientName(name);
    }

    @GetMapping(value = "/findAll")
    public Page<Patient> getPatientAll(@RequestParam(value = "page", defaultValue = "0") int page) {


        return patientService.getPatientAll(page);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")})
    @PostMapping()
    public ResponseEntity<PatientResponseDto> createPatient(@RequestBody @Valid PatientDto patientDto) {
        PatientResponseDto patientResponseDto = patientService.savePatient(patientDto);

        return ResponseEntity.status(HttpStatus.OK).body(patientResponseDto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")})
    @PutMapping()
    public ResponseEntity<PatientResponseDto> changePatient(
            @RequestBody ChangePatientDto changePatientDto) throws Exception {
        PatientResponseDto patientResponseDto = patientService.changePatient(
                changePatientDto.getNumber(),
                changePatientDto.getEmail(),
                changePatientDto.getTel_no_1(),
                changePatientDto.getTel_no_2(),
                changePatientDto.getTel_no_3());

        return ResponseEntity.status(HttpStatus.OK).body(patientResponseDto);

    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")})
    @DeleteMapping()
    public ResponseEntity<String> deletePatient(Long number) throws Exception {
        patientService.deletePatient(number);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

}
