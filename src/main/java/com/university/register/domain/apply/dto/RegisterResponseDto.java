package com.university.register.domain.apply.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterResponseDto {

    private Long courseId;
    private Boolean isRegistered;
    private Boolean isAllTried;
}
