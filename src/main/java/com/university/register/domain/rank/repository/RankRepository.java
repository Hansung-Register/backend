package com.university.register.domain.rank.repository;

import com.university.register.domain.rank.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Long> {
    Rank findTopByStudentIdOrderByIdDesc(int studentId);

    @Query(value = """
        SELECT COUNT(*) + 1
        FROM `rank` a
        WHERE (a.`count` > :count)
           OR (a.`count` = :count AND a.`record` < :record)
        """, nativeQuery = true)
    int findRankByCountAndRecord(@Param("count") int count,
                                 @Param("record") double record);

    boolean existsByStudentId(int studentId);
}
