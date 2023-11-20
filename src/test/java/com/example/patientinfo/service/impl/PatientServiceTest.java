package com.example.patientinfo.service.impl;


import com.example.patientinfo.data.dto.PatientDto;
import com.example.patientinfo.data.dto.PatientResponseDto;
import com.example.patientinfo.data.entity.Gender;
import com.example.patientinfo.data.entity.Patient;
import com.example.patientinfo.data.repository.PatientRepository;
import com.example.patientinfo.service.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({PatientServiceImpl.class})
public class PatientServiceTest {

    @MockBean
    PatientRepository patientRepository;

    @Autowired
    PatientService patientService;

    LocalDate birthDate = LocalDate.parse("2001-12-07");

    @Test
    public void getPatientTest() throws ParseException {
        // given
//        LocalDate birthDate = LocalDate.parse("2001-12-07");

        Patient givenPatient = new Patient();
        givenPatient.setBirth(birthDate);
        givenPatient.setEmail("p011207@naver.com");
        givenPatient.setGender(Gender.valueOf("FEMAIL"));
        givenPatient.setName("psb");
        givenPatient.setTel_no_1("010");
        givenPatient.setTel_no_2("5062");
        givenPatient.setTel_no_3("1710");
        givenPatient.setNumber(123L);

        when(patientRepository.findById(123L))
                .thenReturn(Optional.of(givenPatient));

        // when
        PatientResponseDto productResponseDto = patientService.getPatient(123L);

        // then
        Assertions.assertEquals(productResponseDto.getBirth(), givenPatient.getBirth());
        Assertions.assertEquals(productResponseDto.getName(), givenPatient.getName());
        Assertions.assertEquals(productResponseDto.getNumber(), givenPatient.getNumber());

        verify(patientRepository).findById(123L);
    }

    @Test  //잘 모르겟
    public void getPatientNameTest() throws ParseException {
        // given
//        LocalDate birthDate = LocalDate.parse("2001-12-07");
        Patient givenPatient = new Patient();
        givenPatient.setBirth(birthDate);
        givenPatient.setEmail("p011207@naver.com");
        givenPatient.setGender(Gender.valueOf("FEMAIL"));
        givenPatient.setName("psb");
        givenPatient.setTel_no_1("010");
        givenPatient.setTel_no_2("5062");
        givenPatient.setTel_no_3("1710");

        //직접 DB를 학인하지 않고 테스트에 필요한 결과만 반환
        List<Patient> patientsWithName = Collections.singletonList(givenPatient);
        when(patientRepository.findByName("psb")).thenReturn(patientsWithName);

        // when
        List <Patient> result = patientService.getPatientName("psb");

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Patient foundPatient = result.get(0);

        Assertions.assertEquals(givenPatient.getBirth(), foundPatient.getBirth());
        Assertions.assertEquals(givenPatient.getName(), foundPatient.getName());
        Assertions.assertEquals(givenPatient.getNumber(), foundPatient.getNumber());

        verify(patientRepository).findByName("psb");
    }

    @Test
    public void savePatientTest() throws ParseException {
        // given
//        LocalDate birthDate = LocalDate.parse("2001-12-07");
        when(patientRepository.save(any(Patient.class)))
                .then(returnsFirstArg());

        // when
        PatientResponseDto patientResponseDto = patientService.savePatient(new PatientDto(birthDate, "p011207@naver.com", "tjsqls123.", "FEMAIL", "psb", "010", "5062", "1710"));

        // then
        Assertions.assertEquals(patientResponseDto.getName(), "psb");
        Assertions.assertEquals(patientResponseDto.getEmail(), "p011207@naver.com");

        verify(patientRepository).save(any());
    }

    @Test
    public void ChangePatientTest() throws Exception {
        // Given
        Long patientNumber = 1L;
        String newEmail = "newemail@example.com";
        String newTelNo1 = "111";
        String newTelNo2 = "2222";
        String newTelNo3 = "3333";

        Patient existingPatient = new Patient();
        existingPatient.setNumber(patientNumber);
        existingPatient.setEmail("oldemail@example.com");
        existingPatient.setTel_no_1("000");
        existingPatient.setTel_no_2("0000");
        existingPatient.setTel_no_3("0000");

        Patient updatedPatient = new Patient();
        updatedPatient.setNumber(patientNumber);
        updatedPatient.setEmail(newEmail);
        updatedPatient.setTel_no_1(newTelNo1);
        updatedPatient.setTel_no_2(newTelNo2);
        updatedPatient.setTel_no_3(newTelNo3);

        //가짜 객체를 만들어주는 코드
        when(patientRepository.findById(patientNumber)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(Mockito.any())).thenReturn(updatedPatient);

        // When
        PatientResponseDto result = patientService.changePatient(patientNumber, newEmail, newTelNo1, newTelNo2, newTelNo3);

        // Then
        Assertions.assertEquals(result.getNumber(), 1L);
        Assertions.assertEquals(result.getEmail(), "newemail@example.com");
        Assertions.assertEquals(result.getTel_no_1(), "111");
        Assertions.assertEquals(result.getTel_no_2(), "2222");
        Assertions.assertEquals(result.getTel_no_3(), "3333");

        // Verify that the repository methods were called
        Mockito.verify(patientRepository).findById(patientNumber);
        Mockito.verify(patientRepository).save(existingPatient);
    }

    @Test
    public void deletePatientTest() throws Exception {
        // given
        Mockito.doNothing().when(patientRepository).deleteById(Mockito.any());

        // when
        patientService.deletePatient(123L);

        // then
        verify(patientRepository).deleteById(123L);
    }
}
