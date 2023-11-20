package com.example.patientinfo.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PatientDto {

    @JsonFormat(pattern = "yyMMdd", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyMMdd")
    private LocalDate birth;

    @Email
    private String email;

    private String pwd;

    private String gender;

    private String name;

    @Pattern(regexp = "^[0-9]{3}", message = "3자리 숫자만 입력해주세요")
    private String tel_no_1;
    @Pattern(regexp = "^[0-9]{4}", message = "4자리 숫자만 입력해주세요")
    private String tel_no_2;
    @Pattern(regexp = "^[0-9]{4}", message = "4자리 숫자만 입력해주세요")
    private String tel_no_3;
}