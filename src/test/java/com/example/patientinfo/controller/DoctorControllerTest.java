package com.example.patientinfo.controller;

import com.example.patientinfo.data.dto.*;
import com.example.patientinfo.service.impl.DoctorServiceImpl;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DoctorController.class)
@MockBean(JpaMetamodelMappingContext.class)
class DoctorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DoctorServiceImpl doctorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("MockMvc를 통한 Doctor 데이터 가져오기 테스트")
    void getDoctorTest() throws Exception {
        // given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드
        given(doctorService.getDoctor(123L)).willReturn(
                new DoctorResponseDto(123L, "011207", "p011207@naver.com","tjsqls123.", "FEMAIL", "psb", "sasa", "010", "5062", "1710"));

        String patientId = "123";

        // andExpect : 기대하는 값이 나왔는지 체크해볼 수 있는 메소드
        mockMvc.perform(
                        get("/V2/API/findNumber?number=" + patientId))
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
        verify(doctorService).getDoctor(123L);
    }

    @Test
    @DisplayName("Doctor 데이터 생성 테스트")
    void createPatientTest() throws Exception {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setName("psb");

        DoctorResponseDto dummyResponse = new DoctorResponseDto();
        dummyResponse.setNumber(1L);
        dummyResponse.setName("psb");

        Mockito.when(doctorService.saveDoctor(doctorDto)).thenReturn(dummyResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/V2/API")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("psb"));
    }

    @Test
    @DisplayName("Doctor 데이터 수정 테스트")
    void changeDoctorTest() throws Exception {
        ChangedDoctorDto changedDoctorDto = new ChangedDoctorDto();
        changedDoctorDto.setNumber(1L);
        changedDoctorDto.setPwd("21242141");
        changedDoctorDto.setTel_no_1("123");
        changedDoctorDto.setTel_no_2("4567");
        changedDoctorDto.setTel_no_3("8901");

        DoctorResponseDto dummyResponse = new DoctorResponseDto();
        dummyResponse.setNumber(1L);
        dummyResponse.setPwd("21242141");
        dummyResponse.setTel_no_1("123");
        dummyResponse.setTel_no_2("4567");
        dummyResponse.setTel_no_3("8901");

        Mockito.when(doctorService.changeDoctor(
                changedDoctorDto.getNumber(),
                changedDoctorDto.getPwd(),
                changedDoctorDto.getTel_no_1(),
                changedDoctorDto.getTel_no_2(),
                changedDoctorDto.getTel_no_3())).thenReturn(dummyResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/V2/API")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changedDoctorDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pwd").value("21242141"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tel_no_1").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tel_no_2").value("4567"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tel_no_3").value("8901"));
    }
}