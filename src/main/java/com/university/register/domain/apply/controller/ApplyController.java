package com.university.register.domain.apply.controller;

import com.university.register.domain.apply.dto.*;
import com.university.register.domain.apply.service.ApplyService;
import com.university.register.global.response.ApiResponse;
import com.university.register.global.response.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/apply")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    //수강신청 Go~
    @PostMapping("/start")
    public ResponseEntity<ApiResponse<LocalDateTime>> letsGo(Integer studentId) {
        applyService.LetsGo(studentId);
        return ApiResponse.toResponseEntity(SuccessCode.OK, null);
    }

    //과목 수강신청 => 프론트에서는 isAllTried 값을 받아 True일 때 수강신청 결과 저장 컨트롤러 호출
    @PostMapping("/register/{courseId}")
    public ResponseEntity<ApiResponse<RegisterResponseDto>> registerCourse(@PathVariable Long courseId, @RequestParam Integer studentId, @RequestParam String name) {
        RegisterResponseDto registerResponseDto = applyService.registerAndMaybeFinalize(courseId, studentId, name);
        return ApiResponse.toResponseEntity(SuccessCode.OK, registerResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto response = applyService.login(request);
        return ApiResponse.toResponseEntity(SuccessCode.OK, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestParam Integer studentId) {
        applyService.logout(studentId);
        return ApiResponse.toResponseEntity(SuccessCode.OK, "로그아웃 되었습니다.");
    }
}
