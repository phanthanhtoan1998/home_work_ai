package com.example.dto.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseConfig {

    public static final String SUCCESS_CODE = "00";
    public static final String ERROR_CODE = "01";

    public static <T> ResponseEntity<ResponseDto<T>> success(T body) {
        ResponseDto<T> responseDto = new ResponseDto<>(SUCCESS_CODE, "Success", body);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseDto<T>> error(HttpStatus httpStatus, String errorCode, String message) {
        ResponseDto<T> responseDto = new ResponseDto<>(errorCode, message, null);
        return new ResponseEntity<>(responseDto, httpStatus);
    }
}
