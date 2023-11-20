package com.example.patientinfo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DoctorResponseDto {

    private Long number;

    private String birth;

    private String email;
    private String pwd;

    private String gender;

    private String name;

    private String part;

    private String tel_no_1;
    private String tel_no_2;
    private String tel_no_3;
}
