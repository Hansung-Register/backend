package com.university.register.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 에러, 관리자에게 문의 바랍니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "403", "금지된 요청입니다."),

    STUDENT_ID_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER4091", "이미 존재하는 학번입니다."),
    COURSE_ALREADY_EXISTS(HttpStatus.CONFLICT, "COURSE4091", "이미 존재하는 과목명입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
