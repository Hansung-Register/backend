package com.university.register.domain.registrationsession.repository;

import com.university.register.domain.registrationsession.entity.RegistrationSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationSessionRepository extends JpaRepository<RegistrationSession, Long> {
    Optional<RegistrationSession> findByStudentId(Integer studentId);
    Optional<RegistrationSession> findTopByStudentIdOrderByStartTimeDesc(Integer studentId);
}
