package com.university.register.domain.apply.service;

import com.university.register.domain.apply.dto.*;
import com.university.register.domain.apply.entity.Apply;
import com.university.register.domain.apply.repository.ApplyRepository;
import com.university.register.domain.course.entity.Course;
import com.university.register.domain.course.repository.CourseRepository;
import com.university.register.domain.rank.entity.Rank;
import com.university.register.domain.rank.repository.RankRepository;
import com.university.register.domain.registrationsession.entity.RegistrationSession;
import com.university.register.domain.registrationsession.repository.RegistrationSessionRepository;
import com.university.register.global.exception.ApiException;
import com.university.register.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyService {

    private final CourseRepository courseRepository;
    private final ApplyRepository applyRepository;
    private final RegistrationSessionRepository registrationSessionRepository;
    private final RankRepository rankRepository;

    //수강신청하러 가기 버튼
    @Transactional
    public void LetsGo(int studentId) {
        registrationSessionRepository.save(
                RegistrationSession.builder()
                .studentId(studentId)
                .startTime(LocalDateTime.now())
                .build()
        );
        log.info("학생아이디 {} 수강신청 시작 시간 {}", studentId, LocalDateTime.now());
    }

    @Transactional
    public RegisterResponseDto registerAndMaybeFinalize(Long courseId, Integer studentId, String name) {
        // 1) 수강신청 시도
        RegistrationSession session = registrationSessionRepository
                .findTopByStudentIdOrderByStartTimeDesc(studentId)
                .orElseThrow(() -> new ApiException(ErrorCode.FORBIDDEN));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));

        double time = course.getTime();
        LocalDateTime deadline = session.getStartTime().plusNanos((long) (time * 1_000_000_000));
        boolean isRegistered = LocalDateTime.now().isBefore(deadline);
        log.info("학생아이디 {} 수강신청 과목 {}, 시도 시간 {}, 마감 시간 {}, 성공 여부 {}", studentId, courseId, LocalDateTime.now(), deadline, isRegistered);

        applyRepository.save(Apply.builder()
                .studentId(studentId)
                .courseId(courseId)
                .isSuccess(isRegistered)
                .appliedAt(LocalDateTime.now())
                .build());

        // 2) 전체 시도 여부 체크
        int totalCourses = (int) courseRepository.count();
        int triedCourses = applyRepository.countDistinctCourseIdByStudentId(studentId);
        boolean isAllTried = (triedCourses == totalCourses);
        log.info("학생아이디 {} 시도 과목 수 {} 전체 과목 수 {} 모두 시도 여부 {}", studentId, triedCourses, totalCourses, isAllTried);

        // 3) 모두 시도했다면 최종 집계 + 저장
        if (isAllTried) {
            log.info("학생아이디 {} 모든 과목 수강신청 시도 완료, 최종 집계 시작", studentId);
            int successCount = applyRepository.countByStudentIdAndIsSuccessTrue(studentId);

            LocalDateTime start = session.getStartTime();
            LocalDateTime end = applyRepository.findLastAppliedAtByStudentId(studentId).orElse(start);
            double record = Math.round((java.time.Duration.between(start, end).toNanos() / 1_000_000_000.0) * 1000) / 1000.0;

            rankRepository.save(Rank.builder()
                    .studentId(studentId)
                    .name(name)
                    .count(successCount)
                    .record(record)
                    .build());

            applyRepository.deleteByStudentId(studentId);
        }

        return new RegisterResponseDto(courseId, isRegistered, isAllTried);
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        log.info("학생아이디 {} 로그인 처리", dto.getStudentId());
        return new LoginResponseDto(dto.getStudentId(), dto.getName());
    }


    public void logout(int studentId) {
        log.info("학생아이디 {} 로그아웃 처리", studentId);
    }
}
