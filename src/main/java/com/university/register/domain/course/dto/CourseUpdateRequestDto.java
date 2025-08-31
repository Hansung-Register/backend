package com.university.register.domain.course.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseUpdateRequestDto {

    private String name;

    private Integer remain;

    private Integer basket;

    private Long time;
}
