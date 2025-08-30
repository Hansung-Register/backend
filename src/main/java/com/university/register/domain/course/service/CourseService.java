package com.university.register.domain.course.service;

import com.university.register.domain.course.dto.CourseAddRequestDto;
import com.university.register.domain.course.dto.CourseAdminDto;
import com.university.register.domain.course.dto.CourseDto;
import com.university.register.domain.course.entity.Course;
import com.university.register.domain.course.repository.CourseRepository;
import com.university.register.global.exception.ApiException;
import com.university.register.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    //과목 등록
    @Transactional
    public void applyCourse(CourseAddRequestDto request) {
        if(courseRepository.existsByName(request.getName())) {
            throw new ApiException(ErrorCode.COURSE_ALREADY_EXISTS);
        }

        if(request.getBasket() == null || request.getRemain() == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        Course course;
        if(request.getTime() != null){
            course = new Course(request.getName(), request.getRemain(), request.getBasket(), request.getTime());
        } else {
            course = new Course(request.getName(), request.getRemain(), request.getBasket());
        }
        courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ApiException(ErrorCode.COURSE_NOT_FOUND));
        courseRepository.delete(course);
    }

    //전체 과목 조회
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(course -> new CourseDto(course.getId(), course.getName(), course.getRemain(), course.getBasket(), course.getStatus()))
                .toList();
    }

    //관리자용 전체 과목 조회
    public List<CourseAdminDto> getAllAdminCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(course -> new CourseAdminDto(course.getId(), course.getName(), course.getRemain(), course.getBasket(), course.getTime()))
                .toList();
    }
}
