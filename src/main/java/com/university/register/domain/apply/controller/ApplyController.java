package com.university.register.domain.apply.controller;

import com.university.register.domain.apply.dto.RecordResponseDto;
import com.university.register.domain.apply.dto.RegisterResponseDto;
import com.university.register.domain.apply.dto.SaveRequestDto;
import com.university.register.domain.apply.dto.SaveResponseDto;
import com.university.register.domain.apply.service.ApplyService;
import com.university.register.global.response.ApiResponse;
import com.university.register.global.response.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/apply")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    //수강신청 Go~
    @PostMapping("/start")
    public ResponseEntity<ApiResponse<Void>> legsGo() {
        applyService.LetsGo();
        return ApiResponse.toResponseEntity(SuccessCode.OK, null);
    }

    //과목 수강신청 => 프론트에서는 isAllTried 값을 받아 True일 때 수강신청 결과 저장 컨트롤러 호출
    @PostMapping("/register/{courseId}")
    public ResponseEntity<ApiResponse<RegisterResponseDto>> registerCourse(@PathVariable Long courseId) {
        Boolean isRegistered = applyService.registerCourse(courseId);
        Boolean isAllTried = applyService.checkAllCoursesTried();
        return ApiResponse.toResponseEntity(SuccessCode.OK, new RegisterResponseDto(courseId, isRegistered, isAllTried));
    }

    //게인 결과 조회
    @GetMapping("/result/my")
    public ResponseEntity<ApiResponse<RecordResponseDto>> getMyResult() {
        RecordResponseDto response = applyService.getMyRecord();
        return ApiResponse.toResponseEntity(SuccessCode.OK, response);
    }

    //전체 결과 조회
    @GetMapping("/result/all")
    public ResponseEntity<ApiResponse<List<SaveResponseDto>>> getAllResult() {
        List<SaveResponseDto> response = applyService.getAllRank();
        return ApiResponse.toResponseEntity(SuccessCode.OK, response);
    }

    //수강신청 결과 저장
    @PostMapping("/register/save")
    public ResponseEntity<ApiResponse<SaveResponseDto>> saveApplyResult(@Valid @RequestBody SaveRequestDto dto) {
        SaveResponseDto response = applyService.save(dto);
        return ApiResponse.toResponseEntity(SuccessCode.CREATED, response);
    }

    //수강신청 상태 초기화
    @PostMapping("/reset")
    public ResponseEntity<ApiResponse<Void>> resetApply() {
        applyService.resetRegisteredStatus();
        return ApiResponse.toResponseEntity(SuccessCode.OK, null);
    }
}
