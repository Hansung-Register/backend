package com.university.register.domain.apply.service;

import com.university.register.domain.apply.dto.RecordResponseDto;
import com.university.register.domain.apply.dto.SaveRequestDto;
import com.university.register.domain.apply.dto.SaveResponseDto;
import com.university.register.domain.apply.entity.Apply;
import com.university.register.domain.apply.repository.ApplyRepository;
import com.university.register.domain.course.entity.Course;
import com.university.register.domain.course.repository.CourseRepository;
import com.university.register.global.exception.ApiException;
import com.university.register.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static com.university.register.domain.course.entity.Status.FAILURE;
import static com.university.register.domain.course.entity.Status.SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyService {

    private final CourseRepository courseRepository;
    private final ApplyRepository applyRepository;

    private LocalDateTime REGISTRATION_START;
    private double record = 0;

    //수강신청하러 가기 버튼
    public void LetsGo(){
        REGISTRATION_START = LocalDateTime.now();
        log.info("수강신청 시작 시간 {}", REGISTRATION_START);
    }

    //수강 신청
    @Transactional
    public Boolean registerCourse(Long courseId) {
        if (REGISTRATION_START == null || LocalDateTime.now().isBefore(REGISTRATION_START)) {
            log.info("수강신청 시작 전 or 시작 시간이 null");
            throw new ApiException(ErrorCode.FORBIDDEN);
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));

        boolean isRegistered = false;

        double time = course.getTime();
        if (time >= Long.MAX_VALUE) {
            // 마감 없음: 항상 성공 처리
            course.setStatus(SUCCESS);
            log.info("과목 {} 수강신청 성공 (마감 없음, 시도: {})", course.getName(), LocalDateTime.now());
        } else {
            long seconds = (long) time;
            long nanos = (long) ((time - seconds) * 1_000_000_000);
            LocalDateTime deadline = REGISTRATION_START.plusSeconds(seconds).plusNanos(nanos);
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(deadline)) {
                course.setStatus(FAILURE);
                log.info("과목 {} 수강신청 실패 (마감: {}, 시도: {})", course.getName(), deadline, now);
            } else {
                course.setStatus(SUCCESS);
                log.info("과목 {} 수강신청 성공 (마감: {}, 시도: {})", course.getName(), deadline, now);
                isRegistered = true;
            }
        }
        courseRepository.save(course);

        return isRegistered;
    }

    //수강신청 현황 => 모든 과목이 수강신청 버튼이 눌렸는지 체크
    @Transactional
    public Boolean checkAllCoursesTried() {
        Boolean result = courseRepository.countByStatus(SUCCESS) + courseRepository.countByStatus(FAILURE)
                == courseRepository.count();

        if(result) {
            log.info("모든 과목 수강신청 완료");
            record = java.time.Duration.between(REGISTRATION_START, LocalDateTime.now()).toNanos() / 1_000_000_000.0;
            record = Math.round(record * 1000) / 1000.0; // 소수점 3째자리까지 반올림
            REGISTRATION_START = null; // Reset the registration start time
        }
        return result;
    }

    //전체 결과 조회
    public List<SaveResponseDto> getAllRank() {
        return applyRepository.findAll().stream()
                .sorted(Comparator.comparingInt(apply -> applyRepository.findRankByCountAndRecord(apply.getCount(), apply.getRecord())))
                .map(apply -> new SaveResponseDto(apply.getCount(), apply.getRecord(),
                        applyRepository.findRankByCountAndRecord(apply.getCount(), apply.getRecord()), apply.getStudentId(), apply.getName()))
                .toList();
    }

    //개인 결과 조회
    public RecordResponseDto getMyRecord() {
        return new RecordResponseDto(record, courseRepository.countByStatus(SUCCESS));
    }

    //수강신청 결과 저장
    @Transactional
    public SaveResponseDto save(SaveRequestDto dto) {
        if(applyRepository.existsByStudentId(dto.getStudentId())) {
            throw new ApiException(ErrorCode.STUDENT_ID_ALREADY_EXISTS);
        }

        int count = courseRepository.countByStatus(SUCCESS);
        int rank = applyRepository.findRankByCountAndRecord(count, record);
        applyRepository.save(dto.toEntity(count, record, dto.getStudentId(), dto.getName()));
        SaveResponseDto saveResponseDto = new SaveResponseDto(count, record, rank, dto.getStudentId(), dto.getName());

        List<Course> courses = courseRepository.findAll();
        courses.forEach(course -> course.setStatus(null));
        courseRepository.saveAll(courses);

        return saveResponseDto;
    }

    //수강신청 상태 초기화
    @Transactional
    public void resetRegisteredStatus() {
        List<Course> courses = courseRepository.findAll();
        courses.forEach(course -> course.setStatus(null));
        courseRepository.saveAll(courses);
        log.info("수강신청 상태 초기화 완료");
    }
}
