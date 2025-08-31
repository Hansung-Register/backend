package com.university.register.domain.rank.controller;

import com.university.register.domain.rank.dto.RankResponseDto;
import com.university.register.domain.rank.service.RankService;
import com.university.register.global.response.ApiResponse;
import com.university.register.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rank")
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<RankResponseDto>> getMyRank(Integer studentId) {
        RankResponseDto response = rankService.getMyRank(studentId);
        return ApiResponse.toResponseEntity(SuccessCode.OK, response);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RankResponseDto>>> getAllRank() {
        List<RankResponseDto> response = rankService.getAllRank();
        return ApiResponse.toResponseEntity(SuccessCode.OK, response);
    }
}
