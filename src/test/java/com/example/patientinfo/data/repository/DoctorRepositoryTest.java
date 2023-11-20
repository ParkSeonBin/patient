package com.example.patientinfo.data.repository;

import com.example.patientinfo.data.entity.Doctor;
import com.example.patientinfo.data.entity.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DoctorRepositoryTest {
    @AfterEach //  Test가 하나 끝날때 마다 repository를 비워줌
    public void afterEach() {
        doctorRepository.deleteAll();
    }

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    void saveTest() {
        //given
        Doctor doctor = Doctor.builder()
                .birth("011207")
                .email("example@example.com")
                .pwd("password")
                .gender(Gender.FEMAIL)
                .name("John Doe")
                .part("asas")
                .tel_no_1("123")
                .tel_no_2("4567")
                .tel_no_3("8901")
                .build();

        // when
        Doctor savedDoctor = doctorRepository.save(doctor);

        // then
        assertEquals(doctor, savedDoctor);


    }

    @Test
    void findByName() {
        // given
        Doctor doctor = Doctor.builder()
                .birth("011207")
                .email("example@example.com")
                .pwd("password")
                .gender(Gender.FEMAIL)
                .name("John Doe")
                .tel_no_1("123")
                .tel_no_2("4567")
                .tel_no_3("8901")
                .build();
        doctorRepository.save(doctor);

        // when
        List<Doctor> doctorsFoundByName = doctorRepository.findByName(doctor.getName());

        // Then
        assertEquals(1, doctorsFoundByName.size()); // Ensure that only one patient is found

        Doctor doctorFound = doctorsFoundByName.get(0); // Get the first (and only) patient found
        assertEquals(doctor, doctorFound);
    }

    @Test
    void findById() {
        // given
        Doctor doctor = Doctor.builder()
                .birth("011207")
                .email("example@example.com")
                .pwd("password")
                .gender(Gender.FEMAIL)
                .name("John Doe")
                .tel_no_1("123")
                .tel_no_2("4567")
                .tel_no_3("8901")
                .build();
        doctorRepository.save(doctor);

        // when
        Optional<Doctor> doctorFoundById = doctorRepository.findById(doctor.getNumber());

        // Then
        assertTrue(doctorFoundById.isPresent());
        assertEquals(doctor, doctorFoundById.get());
    }

    @Test
    void findAll() throws ParseException {
        // given
        Doctor doctor = new Doctor();
        doctor.setBirth("011207");
        doctor.setEmail("111111@naver.com");
        doctor.setGender(Gender.valueOf("MAIL"));
        doctor.setName("aaa");
        doctor.setTel_no_1("010");
        doctor.setTel_no_2("1111");
        doctor.setTel_no_3("1111");

        Doctor doctor2 = new Doctor();
        doctor2.setBirth("011207");
        doctor2.setEmail("111111@naver.com");
        doctor2.setGender(Gender.valueOf("MAIL"));
        doctor2.setName("aaa");
        doctor2.setTel_no_1("010");
        doctor2.setTel_no_2("1111");
        doctor2.setTel_no_3("1111");

        Doctor doctor3 = new Doctor();
        doctor3.setBirth("011207");
        doctor3.setEmail("222222@naver.com");
        doctor3.setGender(Gender.valueOf("FEMAIL"));
        doctor3.setName("bbb");
        doctor3.setTel_no_1("010");
        doctor3.setTel_no_2("2222");
        doctor3.setTel_no_3("2222");

        doctorRepository.save(doctor);
        doctorRepository.save(doctor2);
        doctorRepository.save(doctor3);

        // when
        List<Doctor> result = doctorRepository.findAll();

        // Then
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    public void deleteByNumber(){
        Doctor doctor = Doctor.builder()
                .birth("011207")
                .email("example@example.com")
                .pwd("password")
                .gender(Gender.FEMAIL)
                .name("John Doe")
                .tel_no_1("123")
                .tel_no_2("4567")
                .tel_no_3("8901")
                .build();

        Doctor deletePatient = doctorRepository.save(doctor);

        try {
            doctorRepository.deleteById(deletePatient.getNumber());
        } catch (EmptyResultDataAccessException ignored) {
        }

        assertThat(doctorRepository.count()).isEqualTo(0);
    }
}