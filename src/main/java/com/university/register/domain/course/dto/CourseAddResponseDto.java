package com.university.register.domain.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseAddResponseDto {

    private Long id;
    private String name;
    private int remain;
    private int basket;
    private Long time;
}
