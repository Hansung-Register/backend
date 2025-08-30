package com.university.register.domain.course.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseAddRequestDto {
    @NotBlank(message = "과목명은 필수 입력값입니다.")
    private String name;

    @NotNull
    @Positive
    private Integer remain;

    @NotNull
    @Positive
    private Integer basket;

    private Long time;
}
