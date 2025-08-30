package com.university.register.domain.apply.repository;

import com.university.register.domain.apply.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    boolean existsByStudentId(Integer studentId);

    @Query(value = "SELECT COUNT(*) + 1 " +
            "FROM apply a " +
            "WHERE (a.count > :count) " +
            "   OR (a.count = :count AND a.record < :record)", nativeQuery = true)
    int findRankByCountAndRecord(@Param("count") int count, @Param("record") double record);
}
