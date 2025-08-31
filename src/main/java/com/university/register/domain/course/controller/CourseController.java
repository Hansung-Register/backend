package com.university.register.domain.course.controller;

import com.university.register.domain.course.dto.CourseAddRequestDto;
import com.university.register.domain.course.dto.CourseAdminDto;
import com.university.register.domain.course.dto.CourseDto;
import com.university.register.domain.course.dto.CourseUpdateRequestDto;
import com.university.register.domain.course.service.CourseService;
import com.university.register.global.response.ApiResponse;
import com.university.register.global.response.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    //과목 등록
//    @PostMapping("/add")
//    public ResponseEntity<ApiResponse<String>> addCourse(@Valid @RequestBody CourseAddRequestDto request) {
//        courseService.applyCourse(request);
//        return ApiResponse.toResponseEntity(SuccessCode.CREATED, "과목 등록 성공");
//    }

    //과목 수정
    @PutMapping("/update/{courseId}")
    public ResponseEntity<ApiResponse<String>> updateCourse(@PathVariable Long courseId, @RequestBody CourseUpdateRequestDto request) {
        courseService.updateCourse(courseId, request);
        return ApiResponse.toResponseEntity(SuccessCode.OK, "과목 수정 성공");
    }

    //과목 삭제
//    @DeleteMapping("/delete/{courseId}")
//    public ResponseEntity<ApiResponse<String>> deleteCourse(@PathVariable Long courseId) {
//        courseService.deleteCourse(courseId);
//        return ApiResponse.toResponseEntity(SuccessCode.OK, "과목 삭제 성공");
//    }

    //전체 과목 조회
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CourseDto>>> getAllCourses() {
        return ApiResponse.toResponseEntity(SuccessCode.OK, courseService.getAllCourses());
    }

    //관리자용 전체 과목 조회
    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<List<CourseAdminDto>>> getAllAdminCourses() {
        return ApiResponse.toResponseEntity(SuccessCode.OK, courseService.getAllAdminCourses());
    }
}
