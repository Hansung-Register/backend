package com.university.register.global.exception;

import com.university.register.global.response.ApiResponse;
import com.university.register.global.response.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), e.getData()));
    }

    // ✅ 중복 원인이었던 @ExceptionHandler(MethodArgumentNotValidException) 제거
    //    대신 ResponseEntityExceptionHandler의 보호 메서드를 오버라이드
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(this::toErrorEntry)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.onFailure(ErrorCode.BAD_REQUEST, errors));
    }

    // ✅ 이 예외도 기본 핸들러가 있어 중복될 수 있으므로 오버라이드로 커스터마이즈
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        String msg = ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : ex.getMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.onFailure(ErrorCode.BAD_REQUEST, msg));
    }

    // 이건 기본에서 처리하지 않으니 @ExceptionHandler 유지 OK
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<Map<String, String>> errors = ex.getConstraintViolations().stream()
                .map(cv -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("field", cv.getPropertyPath().toString());
                    map.put("message", cv.getMessage());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.onFailure(ErrorCode.BAD_REQUEST, errors));
    }

    private Map<String, String> toErrorEntry(FieldError fe) {
        Map<String, String> map = new HashMap<>();
        map.put("field", fe.getField());
        map.put("message", fe.getDefaultMessage());
        return map;
    }
}
