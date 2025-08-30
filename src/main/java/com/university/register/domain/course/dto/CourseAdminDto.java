package com.university.register.domain.course.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseAdminDto {
    private Long id;
    private String name;
    private int remain;
    private int basket;
    private Long time;

    public CourseAdminDto(Long id, String name, int remain, int basket, Long time) {
        this.id = id;
        this.name = name;
        this.remain = remain;
        this.basket = basket;
        this.time = time;
    }
}
