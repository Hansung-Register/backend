package com.university.register.domain.rank.service;

import com.university.register.domain.apply.dto.SaveResponseDto;
import com.university.register.domain.rank.dto.RankResponseDto;
import com.university.register.domain.rank.entity.Rank;
import com.university.register.domain.rank.repository.RankRepository;
import com.university.register.global.exception.ApiException;
import com.university.register.global.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RankService {

    private final RankRepository rankRepository;

    public RankResponseDto getMyRank(int studentId) {
        Rank myRank = rankRepository.findTopByStudentIdOrderByIdDesc(studentId);
        if(myRank == null){
            throw new ApiException(ErrorCode.RANK_NOT_FOUND, "Rank not found");
        }
        int rank = rankRepository.findRankByCountAndRecord(myRank.getCount(), myRank.getRecord());
        return new RankResponseDto(myRank.getCount(), myRank.getRecord(), rank, myRank.getStudentId(), myRank.getName());
    }

    public List<RankResponseDto> getAllRank() {
        return rankRepository.findAll().stream()
                .sorted(Comparator.comparingInt(rank -> rankRepository.findRankByCountAndRecord(rank.getCount(), rank.getRecord())))
                .map(rank -> new RankResponseDto(rank.getCount(), rank.getRecord(),
                        rankRepository.findRankByCountAndRecord(rank.getCount(), rank.getRecord()), rank.getStudentId(), rank.getName()))
                .toList();
    }
}
