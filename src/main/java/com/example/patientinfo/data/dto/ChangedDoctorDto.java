package com.example.patientinfo.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangedDoctorDto {

    private Long number;

    private String pwd;

    private String tel_no_1;
    private String tel_no_2;
    private String tel_no_3;

    public ChangedDoctorDto(Long number, String pwd, String tel_no_1, String tel_no_2, String tel_no_3) {
        this.number = number;
        this.pwd = pwd;
        this.tel_no_1 = tel_no_1;
        this.tel_no_2 = tel_no_2;
        this.tel_no_3 = tel_no_3;

    }

    public ChangedDoctorDto() {
    }

}