# 🔒 PatientInfo
"환자 정보 간단 입력 시스템"
<br/><br/>

## 💻기능 소개
__PatientController    /V1/API__

C - 환자 정보 입력

R - 환자 정보 단일 검색, 전체 검색

U - 환자 정보 수정 (이메일, 전화번호)

D - 단일 삭제
<br/>

__DoctorController    /V2/API__

C - 의사 정보 입력

R - 의사 정보 단일 검색, 전체 검색

U - 의사 정보 수정 (비밀번호, 전화번호)

D - 단일 삭제
<br/><br/>

## 📆개발 기간
23.10.23 - 23.10.31
<br/><br/>

## ⚙️개발 환경
IDE - InteliJ

JAVA - JDK11

SpringBoot - 2.7.17

Build - Maven
<br/><br/>

## ✏️ 배운점
Optional, List, Page


DB -> DAO(Service) -> DTO

DB에서 값을 받아오는 것은 DAO or Service, 받아온 DB 값을 저장하고 프로그램 내에서 계층 간 전달 역할을 하는 것은 DTO


Enum -> @Enumerated(EnumType.STRING)

ModelMapper -> Object Mappring, ServuceImpl에서 반복되는 Get,Set을 줄여준다.
