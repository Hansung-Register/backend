package com.university.register.domain.course.repository;

import com.university.register.domain.course.entity.Course;
import com.university.register.domain.course.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByName(String name);
}
