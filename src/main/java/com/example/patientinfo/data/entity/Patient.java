package com.example.patientinfo.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "tn_cm_trgter_info")
public class Patient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private Long number;

    //중복이 싫으면 사용자 정의 날짜 직렬 변환기 클래스를 생성해야함
    //@JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(pattern = "yyMMdd", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyMMdd")
    private LocalDate birth;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String pwd;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 50)
    private String name;

    @Column(length = 3)
    private String tel_no_1;
    @Column(length = 4)
    private String tel_no_2;
    @Column(length = 4)
    private String tel_no_3;
}