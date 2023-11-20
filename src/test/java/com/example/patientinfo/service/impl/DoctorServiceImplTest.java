package com.example.patientinfo.service.impl;

import com.example.patientinfo.data.dto.DoctorDto;
import com.example.patientinfo.data.dto.DoctorResponseDto;
import com.example.patientinfo.data.entity.Doctor;
import com.example.patientinfo.data.entity.Gender;
import com.example.patientinfo.data.repository.DoctorRepository;
import com.example.patientinfo.service.DoctorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({DoctorServiceImpl.class})
class DoctorServiceImplTest {

    @MockBean
    DoctorRepository doctorRepository;

    @Autowired
    DoctorService doctorService;

    @Test
    void getDoctorTest() {
        //given
        Doctor givenDoctor = new Doctor();
        givenDoctor.setBirth("011207");
        givenDoctor.setEmail("p011207@naver.com");
        givenDoctor.setPwd("tjsqls123.");
        givenDoctor.setGender(Gender.valueOf("FEMAIL"));
        givenDoctor.setName("psb");
        givenDoctor.setPart("internist");
        givenDoctor.setTel_no_1("010");
        givenDoctor.setTel_no_2("5062");
        givenDoctor.setTel_no_3("1710");
        givenDoctor.setNumber(123L);
        //when
        when(doctorRepository.findById(123L)).thenReturn(Optional.of(givenDoctor));
        DoctorResponseDto doctorResponseDto = doctorService.getDoctor(123L);
        //then
        Assertions.assertEquals(doctorResponseDto.getBirth(), givenDoctor.getBirth());
        Assertions.assertEquals(doctorResponseDto.getName(), givenDoctor.getName());
        Assertions.assertEquals(doctorResponseDto.getNumber(), givenDoctor.getNumber());

        verify(doctorRepository).findById(123L);
    }

    @Test
    void getDoctorName() {
        Doctor givenDoctor = new Doctor();
        givenDoctor.setBirth("011207");
        givenDoctor.setEmail("p011207@naver.com");
        givenDoctor.setPwd("tjsqls123.");
        givenDoctor.setGender(Gender.valueOf("FEMAIL"));
        givenDoctor.setName("psb");
        givenDoctor.setPart("internist");
        givenDoctor.setTel_no_1("010");
        givenDoctor.setTel_no_2("5062");
        givenDoctor.setTel_no_3("1710");
        givenDoctor.setNumber(123L);
        //when //잘 모르겟
        when(doctorRepository.findByName("psb")).thenReturn(List.of(givenDoctor));
        List<Doctor> result = doctorService.getDoctorName("psb");
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Doctor foundDoctor = result.get(0);

        Assertions.assertEquals(foundDoctor.getBirth(), givenDoctor.getBirth());
        Assertions.assertEquals(foundDoctor.getName(), givenDoctor.getName());
        Assertions.assertEquals(foundDoctor.getNumber(), givenDoctor.getNumber());

        verify(doctorRepository).findByName("psb");
    }

    @Test
    void getDoctorAll() {
        //given
        Pageable pageable = PageRequest.of(0, 5);

        Doctor doctor1 = new Doctor();
        Doctor doctor2 = new Doctor();
        Doctor doctor3 = new Doctor();

        // when
        when(doctorRepository.findAll(pageable)).thenReturn(new PageImpl<>(Arrays.asList(doctor1, doctor2, doctor3)));
        Page<Doctor> result = doctorService.getDoctorAll(0);
        // Then
        verify(doctorRepository).findAll(pageable);
        assertEquals(3, result.getTotalElements());
    }

    @Test
    void saveDoctor() {
        //given
        when(doctorRepository.save(any(Doctor.class))).then(returnsFirstArg());
        //when
        DoctorResponseDto doctorResponseDto = doctorService.saveDoctor(new DoctorDto("011207", "p011207@naver.com", "tjsqls123.", "FEMAIL", "psb", "internist", "010", "5062", "1710"));
        //then
        Assertions.assertEquals(doctorResponseDto.getName(), "psb");
        Assertions.assertEquals(doctorResponseDto.getEmail(), "p011207@naver.com");

        verify(doctorRepository).save(any());
    }

    @Test
    void changeDoctor() throws Exception {
        // Given
        Long doctorNumber = 1L;
        String newPws = "aaaaaa";
        String newTelNo1 = "111";
        String newTelNo2 = "2222";
        String newTelNo3 = "3333";

        Doctor existingDoctor = new Doctor();
        existingDoctor.setNumber(doctorNumber);
        existingDoctor.setPwd("tjsqls123.");
        existingDoctor.setTel_no_1("000");
        existingDoctor.setTel_no_2("0000");
        existingDoctor.setTel_no_3("0000");

        Doctor updatedPatient = new Doctor();
        updatedPatient.setNumber(doctorNumber);
        updatedPatient.setEmail(newPws);
        updatedPatient.setTel_no_1(newTelNo1);
        updatedPatient.setTel_no_2(newTelNo2);
        updatedPatient.setTel_no_3(newTelNo3);

        //가짜 객체를 만들어주는 코드
        when(doctorRepository.findById(doctorNumber)).thenReturn(Optional.of(existingDoctor));
        when(doctorRepository.save(Mockito.any())).thenReturn(updatedPatient);

        // When
        DoctorResponseDto result = doctorService.changeDoctor(doctorNumber, newPws, newTelNo1, newTelNo2, newTelNo3);

        // Then
        Assertions.assertEquals(result.getEmail(), "aaaaaa");
        Assertions.assertEquals(result.getTel_no_1(), "111");
        Assertions.assertEquals(result.getTel_no_2(), "2222");
        Assertions.assertEquals(result.getTel_no_3(), "3333");

        // Verify that the repository methods were called
        Mockito.verify(doctorRepository).findById(doctorNumber);
        Mockito.verify(doctorRepository).save(existingDoctor);
    }

    @Test
    void deleteDoctor() throws Exception {
        // given
        Mockito.doNothing().when(doctorRepository).deleteById(Mockito.any());

        // when
        doctorService.deleteDoctor(123L);

        // then
        verify(doctorRepository).deleteById(123L);
    }
}