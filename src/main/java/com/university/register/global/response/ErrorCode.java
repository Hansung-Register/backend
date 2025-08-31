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
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4041", "존재하지 않는 회원입니다."),
    INVALID_NAME(HttpStatus.BAD_REQUEST, "USER4002", "이름이 일치하지 않습니다."),

    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "COURSE4041", "존재하지 않는 과목입니다."),
    COURSE_ALREADY_EXISTS(HttpStatus.CONFLICT, "COURSE4091", "이미 존재하는 과목명입니다."),
    COURSE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "COURSE4002", "과목은 최대 6개까지 등록할 수 있습니다."),

    RANK_NOT_FOUND(HttpStatus.NOT_FOUND, "RANK4041", "기록을 불러올 수 없습니다."),;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
