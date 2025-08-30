package com.university.register.domain.apply.entity;

import com.university.register.domain.course.entity.Status;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Apply{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id")
    private Integer studentId;
    private String name;

    //성공한 과목의 수
    private Integer count;

    //기록
    private double record;
}
