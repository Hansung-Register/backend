package com.university.register.domain.apply.dto;

import com.university.register.domain.apply.entity.Apply;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveRequestDto {

    //학생 정보
    @NotNull
    @Positive
    private Integer studentId;
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    public Apply toEntity(int count, double record, int studentId, String name) {
        return Apply.builder()
                .count(count)
                .record(record)
                .studentId(studentId)
                .name(name)
                .build();
    }
}
