package com.university.register.domain.rank.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RankResponseDto {
    private int count;
    private double record;
    private int rank;
    private Integer studentId;
    private String name;
}
