package com.example.patientinfo.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "tn_cm_user_info")
public class Doctor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private Long number;

    @Column(length = 6)
    private String birth;

    @Column(length = 100)
    private String email;

    @Column(length = 50)
    private String pwd;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String part;

    @Column(length = 3)
    private String tel_no_1;
    @Column(length = 4)
    private String tel_no_2;
    @Column(length = 4)
    private String tel_no_3;
}