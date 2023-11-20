package com.example.patientinfo.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

// 예제 6.20
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientResponseDto {

    private Long number;

    @JsonFormat(pattern = "yyMMdd", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyMMdd")
    private LocalDate birth;

    private String email;

    private String pwd;

    private String gender;

    private String name;

    private String tel_no_1;
    private String tel_no_2;
    private String tel_no_3;
}
