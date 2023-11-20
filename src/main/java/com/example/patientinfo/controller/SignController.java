package com.example.patientinfo.controller;

import com.example.patientinfo.data.dto.DoctorDto;
import com.example.patientinfo.data.dto.PatientDto;
import com.example.patientinfo.data.dto.SignInResultDto;
import com.example.patientinfo.data.dto.SignUpResultDto;
import com.example.patientinfo.service.SignService;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

// 예제 13.28
@RestController
@RequestMapping("/V3/login")
public class SignController {
    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }

    @PostMapping(value = "/patient/sign-in")
    public SignInResultDto patientSignIn(
            @ApiParam(value = "ID", required = true) @RequestParam String id,
            @ApiParam(value = "Password", required = true) @RequestParam String password)
            throws RuntimeException {
        LOGGER.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", id);
        SignInResultDto signInResultDto = signService.patientSignIn(id, password);

        if (signInResultDto.getCode() == 0) {
            LOGGER.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", id,
                    signInResultDto.getToken());
        }
        return signInResultDto;
    }

    @PostMapping(value = "/patient/sign-up")
    public ResponseEntity<SignUpResultDto> patientSignUp(@RequestBody @Valid PatientDto patientDto) throws Exception {
        LOGGER.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}", patientDto.getEmail(), patientDto.getName());
        SignUpResultDto signUpResultDto = signService.patientSignUp(patientDto);
        LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", patientDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(signUpResultDto);
    }

    @PostMapping(value = "/doctor/sign-in")
    public SignInResultDto doctorSignIn(
            @ApiParam(value = "ID", required = true) @RequestParam String id,
            @ApiParam(value = "Password", required = true) @RequestParam String password)
            throws RuntimeException {
        LOGGER.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", id);
        SignInResultDto signInResultDto = signService.doctorSignIn(id, password);

        if (signInResultDto.getCode() == 0) {
            LOGGER.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", id,
                    signInResultDto.getToken());
        }
        return signInResultDto;
    }

    @PostMapping(value = "/doctor/sign-up")
    public ResponseEntity<SignUpResultDto> doctorSignUp(@RequestBody @Valid DoctorDto doctorDto) throws Exception {
        LOGGER.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}", doctorDto.getEmail(), doctorDto.getName());
        SignUpResultDto signUpResultDto = signService.doctorSignUp(doctorDto);
        LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", doctorDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(signUpResultDto);
    }

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest servletRequest) throws Exception {
        signService.logout();

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 로그아웃되었습니다.");
    }
}