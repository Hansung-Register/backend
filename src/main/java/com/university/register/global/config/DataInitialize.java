package com.university.register.global.config;

import com.university.register.domain.course.entity.Course;
import com.university.register.domain.course.repository.CourseRepository;
import com.university.register.domain.apply.entity.Apply;
import com.university.register.domain.apply.repository.ApplyRepository;
import com.university.register.domain.rank.entity.Rank;
import com.university.register.domain.rank.repository.RankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class DataInitialize implements CommandLineRunner {

    private final CourseRepository courseRepository;
    private final ApplyRepository applyRepository;
    private final RankRepository rankRepository;

    @Override
    @Transactional
    public void run(String... args) {
        seedCourses();
        seedApplies();
    }

    private void seedCourses() {
        List<Course> courses = List.of(
                new Course("운영체제", 30, 80),
                new Course("웹프레임워크1", 30, 100),
                new Course("고급모바일프로그래밍", 20, 90),
                new Course("알고리즘", 30, 80),
                new Course("컴퓨터구조", 30, 70),
                new Course("데이터베이스", 30, 90)
        );

        for (Course c : courses) {
            if (!courseRepository.existsByName(c.getName())) {
                courseRepository.save(c);
                log.info("[DataInit] Course inserted: {} (remain={}, basket={})", c.getName(), c.getRemain(), c.getBasket());
            } else {
                log.info("[DataInit] Course already exists: {}", c.getName());
            }
        }
    }

    private void seedApplies() {
        Integer studentId = 2091083;
        String name = "이한준";
        if (!rankRepository.existsByStudentId(studentId)) {
            Rank rank = Rank.builder()
                    .studentId(studentId)
                    .name(name)
                    .count(6)
                    .record(4.7)
                    .build();
            rankRepository.save(rank);
            log.info("[DataInit] Apply inserted: {} ({})", name, studentId);
        } else {
            log.info("[DataInit] Apply already exists: {} ({})", name, studentId);
        }
    }
}
