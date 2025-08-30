package com.university.register.domain.course.dto;

import com.university.register.domain.course.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDto {
    private Long id;
    private String name;
    private int remain;
    private int basket;
    private Status status;

    public CourseDto(Long id, String name, int remain, int basket, Status status) {
        this.id = id;
        this.name = name;
        this.remain = remain;
        this.basket = basket;
        this.status = status;
    }
}
