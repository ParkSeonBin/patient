package com.example.patientinfo.common;

import lombok.Getter;

// 예제 13.27
// 로그인 결과 데이터
@Getter
public enum CommonResponse {

    SUCCESS(0, "Success"), FAIL(-1, "Fail");

    int code;
    String msg;

    CommonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}