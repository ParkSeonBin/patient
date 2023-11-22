package com.example.patientinfo.service.impl;

import com.example.patientinfo.common.CommonResponse;
import com.example.patientinfo.config.security.JwtTokenProvider;
import com.example.patientinfo.data.dto.DoctorDto;
import com.example.patientinfo.data.dto.PatientDto;
import com.example.patientinfo.data.dto.SignInResultDto;
import com.example.patientinfo.data.dto.SignUpResultDto;
import com.example.patientinfo.data.entity.Doctor;
import com.example.patientinfo.data.entity.Gender;
import com.example.patientinfo.data.entity.Patient;
import com.example.patientinfo.data.repository.DoctorRepository;
import com.example.patientinfo.data.repository.PatientRepository;
import com.example.patientinfo.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


// 예제 13.25
@Service
public class SignServiceImpl implements SignService {

    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);

    public PatientRepository patientRepository;
    public DoctorRepository doctorRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(PatientRepository patientRepository, DoctorRepository doctorRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignUpResultDto patientSignUp(PatientDto patientDto) throws Exception {
        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");
        Patient patient;
        patient = Patient.builder()
                .birth(patientDto.getBirth())
                .email(patientDto.getEmail())
                .pwd(passwordEncoder.encode(patientDto.getPwd()))
                .gender(Gender.valueOf(patientDto.getGender()))
                .name(patientDto.getName())
                .tel_no_1(patientDto.getTel_no_1())
                .tel_no_2(patientDto.getTel_no_2())
                .tel_no_3(patientDto.getTel_no_3())
                //.roles(Collections.singletonList("ROLE_ADMIN"))
                .build();

        Patient savedPatient = patientRepository.save(patient);
        SignUpResultDto signUpResultDto = new SignInResultDto();

        LOGGER.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if (!savedPatient.getName().isEmpty()) {
            LOGGER.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            LOGGER.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignUpResultDto doctorSignUp(DoctorDto doctorDto) throws Exception {
        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");
        Doctor doctor;
        doctor = Doctor.builder()
                .birth(doctorDto.getBirth())
                .email(doctorDto.getEmail())
                .pwd(passwordEncoder.encode(doctorDto.getPwd()))
                .gender(Gender.valueOf(doctorDto.getGender()))
                .name(doctorDto.getName())
                .part(doctorDto.getPart())
                .tel_no_1(doctorDto.getTel_no_1())
                .tel_no_2(doctorDto.getTel_no_2())
                .tel_no_3(doctorDto.getTel_no_3())
                //.roles(Collections.singletonList("ROLE_ADMIN"))
                .build();

        Doctor savedDoctor = doctorRepository.save(doctor);
        SignUpResultDto signUpResultDto = new SignInResultDto();

        LOGGER.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if (!savedDoctor.getName().isEmpty()) {
            LOGGER.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            LOGGER.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignInResultDto patientSignIn(String id, String password) {
        LOGGER.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        Patient patient = patientRepository.findByEmail(id);
        LOGGER.info("[getSignInResult] Id : {}", id);

        LOGGER.info("[getSignInResult] 패스워드 비교 수행");
        if (!passwordEncoder.matches(password, patient.getPwd())) {
            throw new RuntimeException();
        }
        LOGGER.info("[getSignInResult] 패스워드 일치");

        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(patient.getEmail(), "Patient"))
                .build();

        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    @Override
    public SignInResultDto doctorSignIn(String id, String password) {
        LOGGER.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        Doctor doctor = doctorRepository.findByEmail(id);
        LOGGER.info("[getSignInResult] Id : {}", id);

        LOGGER.info("[getSignInResult] 패스워드 비교 수행");
        if (!passwordEncoder.matches(password, doctor.getPwd())) {
            throw new RuntimeException();
        }
        LOGGER.info("[getSignInResult] 패스워드 일치");

        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(doctor.getEmail(), "Doctor"))
                .build();

        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    // 결과 모델에 api 요청 실패 데이터를 세팅해주는 메소드
    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}