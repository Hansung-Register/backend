package com.university.register.domain.apply.repository;

import com.university.register.domain.apply.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    boolean existsByStudentId(Integer studentId);

//    @Query(value = "SELECT COUNT(*) + 1 " +
//            "FROM apply a " +
//            "WHERE (a.count > :count) " +
//            "   OR (a.count = :count AND a.record < :record)", nativeQuery = true)
//    int findRankByCountAndRecord(@Param("count") int count, @Param("record") double record);

    int countByStudentId(Integer studentId);

    int countByStudentIdAndIsSuccessTrue(Integer studentId);

    @Query("SELECT COUNT(DISTINCT a.courseId) FROM Apply a WHERE a.studentId = :studentId")
    int countDistinctCourseIdByStudentId(@Param("studentId") int studentId);

    @Query("select max(a.appliedAt) from Apply a where a.studentId = :studentId")
    Optional<LocalDateTime> findLastAppliedAtByStudentId(@Param("studentId") Integer studentId);

    void deleteByStudentId(Integer studentId);


}
