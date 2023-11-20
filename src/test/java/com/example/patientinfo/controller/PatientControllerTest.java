package com.example.patientinfo.controller;

import com.example.patientinfo.data.dto.ChangePatientDto;
import com.example.patientinfo.data.dto.PatientDto;
import com.example.patientinfo.data.dto.PatientResponseDto;
import com.example.patientinfo.service.impl.PatientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PatientServiceImpl patientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("MockMvc를 통한 Patient 데이터 가져오기 테스트")
    void getPatientTest() throws Exception {

        LocalDate birthDate = LocalDate.parse("2001-12-07");


        // given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드
        given(patientService.getPatient(123L)).willReturn(
                new PatientResponseDto(123L, birthDate, "p011207@naver.com","tjsqls123.", "FEMAIL", "psb", "010", "5062", "1710"));

        String patientId = "123";

        // andExpect : 기대하는 값이 나왔는지 체크해볼 수 있는 메소드
        mockMvc.perform(
                        get("/V1/API/findNumber?number=" + patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birth").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.gender").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.tel_no_1").exists())
                .andExpect(jsonPath("$.tel_no_2").exists())
                .andExpect(jsonPath("$.tel_no_3").exists())
                .andDo(print());

        // verify : 해당 객체의 메소드가 실행되었는지 체크해줌
        verify(patientService).getPatient(123L);
    }


    @Test
    @DisplayName("Patient 데이터 생성 테스트")
    void createPatientTest() throws Exception {
        PatientDto patientDto = new PatientDto();
        patientDto.setName("psb");

        PatientResponseDto dummyResponse = new PatientResponseDto();
        dummyResponse.setNumber(1L);
        dummyResponse.setName("psb");

        Mockito.when(patientService.savePatient(patientDto)).thenReturn(dummyResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/V1/API")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("psb"));
    }

    @Test
    @DisplayName("Patient 데이터 수정 테스트")
    void changePatientTest() throws Exception {
        ChangePatientDto changePatientDto = new ChangePatientDto();
        changePatientDto.setNumber(1L);
        changePatientDto.setEmail("new.email@example.com");
        changePatientDto.setTel_no_1("123");
        changePatientDto.setTel_no_2("4567");
        changePatientDto.setTel_no_3("8901");

        PatientResponseDto dummyResponse = new PatientResponseDto();
        dummyResponse.setNumber(1L);
        dummyResponse.setEmail("new.email@example.com");
        dummyResponse.setTel_no_1("123");
        dummyResponse.setTel_no_2("4567");
        dummyResponse.setTel_no_3("8901");

        Mockito.when(patientService.changePatient(
                changePatientDto.getNumber(),
                changePatientDto.getEmail(),
                changePatientDto.getTel_no_1(),
                changePatientDto.getTel_no_2(),
                changePatientDto.getTel_no_3())).thenReturn(dummyResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/V1/API")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePatientDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("new.email@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tel_no_1").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tel_no_2").value("4567"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tel_no_3").value("8901"));
    }
}
