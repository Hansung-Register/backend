package com.university.register.domain.apply.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveResponseDto {

    private Long id;
    private Integer count;
    private double record;
    private Integer studentId;
    private String name;


    public SaveResponseDto(Integer count, double record, Integer studentId, String name) {
        this.count = count;
        this.record = record;
        this.studentId = studentId;
        this.name = name;
    }
}
