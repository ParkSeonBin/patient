package com.example.patientinfo.data.repository;

import com.example.patientinfo.data.entity.Gender;
import com.example.patientinfo.data.entity.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientRepositoryTest {

    @AfterEach //  Test가 하나 끝날때 마다 repository를 비워줌
    public void afterEach() {
        patientRepository.deleteAll();
    }

    @Autowired
    private PatientRepository patientRepository;

    // Q. JPARepository를 사용하는건데도 Test가 필요한지?
    // 이미 검증된 메서드들이 아닌가?

    @Test
    void saveTest() {
        //given
        Patient patient = Patient.builder()
                .birth(LocalDate.parse("2001-12-07"))
                .email("example@example.com")
                .pwd("password")
                .gender(Gender.FEMAIL)
                .name("John Doe")
                .tel_no_1("123")
                .tel_no_2("4567")
                .tel_no_3("8901")
                .build();

        // when
        Patient savedPatient = patientRepository.save(patient);

        // then
        assertEquals(patient, savedPatient);


    }

    @Test
    void findByName() {
        // given
        Patient patient = Patient.builder()
                .birth(LocalDate.parse("2001-12-07"))
                .email("example@example.com")
                .pwd("password")
                .gender(Gender.FEMAIL)
                .name("John Doe")
                .tel_no_1("123")
                .tel_no_2("4567")
                .tel_no_3("8901")
                .build();
        patientRepository.save(patient);

        // when
        List<Patient> patientsFoundByName = patientRepository.findByName(patient.getName());

        // Then
        assertEquals(1, patientsFoundByName.size()); // Ensure that only one patient is found

        Patient patientFound = patientsFoundByName.get(0); // Get the first (and only) patient found
        assertEquals(patient, patientFound);
    }

    @Test
    void findById() {
        // given
        Patient patient = Patient.builder()
                .birth(LocalDate.parse("2001-12-07"))
                .email("example@example.com")
                .pwd("password")
                .gender(Gender.FEMAIL)
                .name("John Doe")
                .tel_no_1("123")
                .tel_no_2("4567")
                .tel_no_3("8901")
                .build();
        patientRepository.save(patient);

        // when
        Optional<Patient> patientFoundById = patientRepository.findById(patient.getNumber());

        // Then
        assertTrue(patientFoundById.isPresent());
        assertEquals(patient, patientFoundById.get());
    }

    @Test
    void findAll() throws ParseException {
        // given
        LocalDate birthDate = LocalDate.parse("2001-12-07");

        Patient patient = new Patient();
        patient.setBirth(birthDate);
        patient.setEmail("111111@naver.com");
        patient.setGender(Gender.valueOf("MAIL"));
        patient.setName("aaa");
        patient.setTel_no_1("010");
        patient.setTel_no_2("1111");
        patient.setTel_no_3("1111");

        Patient patient1 = new Patient();
        patient1.setBirth(birthDate);
        patient1.setEmail("111111@naver.com");
        patient1.setGender(Gender.valueOf("MAIL"));
        patient1.setName("aaa");
        patient1.setTel_no_1("010");
        patient1.setTel_no_2("1111");
        patient1.setTel_no_3("1111");

        Patient patient2 = new Patient();
        patient2.setBirth(birthDate);
        patient2.setEmail("222222@naver.com");
        patient2.setGender(Gender.valueOf("FEMAIL"));
        patient2.setName("bbb");
        patient2.setTel_no_1("010");
        patient2.setTel_no_2("2222");
        patient2.setTel_no_3("2222");

        patientRepository.save(patient);
        patientRepository.save(patient1);
        patientRepository.save(patient2);

        // when
        List<Patient> result = patientRepository.findAll();

        // Then
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    public void deleteByNumber(){
        Patient patient = Patient.builder()
                .birth(LocalDate.parse("2001-12-07"))
                .email("example@example.com")
                .pwd("password")
                .gender(Gender.FEMAIL)
                .name("John Doe")
                .tel_no_1("123")
                .tel_no_2("4567")
                .tel_no_3("8901")
                .build();
        Patient deletePatient = patientRepository.save(patient);

        try {
            patientRepository.deleteById(deletePatient.getNumber());
        } catch (EmptyResultDataAccessException ignored) {
        }

        assertThat(patientRepository.count()).isEqualTo(0);
    }
}
