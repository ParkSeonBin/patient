package com.example.patientinfo.data.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DoctorDto {

    @Pattern(regexp = "^[0-9]{6}", message = "숫자만 입력해주세요")
    private String birth;

    @Email
    private String email;

    private String pwd;

    private String gender;

    private String name;

    private String part;

    @Pattern(regexp = "^[0-9]{3}", message = "3자리 숫자만 입력해주세요")
    private String tel_no_1;
    @Pattern(regexp = "^[0-9]{4}", message = "4자리 숫자만 입력해주세요")
    private String tel_no_2;
    @Pattern(regexp = "^[0-9]{4}", message = "4자리 숫자만 입력해주세요")
    private String tel_no_3;
}