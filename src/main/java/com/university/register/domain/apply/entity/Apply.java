package com.university.register.domain.apply.entity;

import com.university.register.domain.course.entity.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    private Long courseId;

    boolean isSuccess;

    LocalDateTime appliedAt;
}
