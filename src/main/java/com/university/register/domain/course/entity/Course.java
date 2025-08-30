package com.university.register.domain.course.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer remain;

    private Integer basket;

    @Setter
    private Status status;

    private Long time;

    public Course (String name, Integer remain, Integer basket, Long time){
        this.name = name;
        this.remain = remain;
        this.basket = basket;
        this.status = null;
        this.time = time;
    }

    public Course (String name, Integer remain, Integer basket) {
        this.name = name;
        this.remain = remain;
        this.basket = basket;
        this.status = null;
        setTime(basket, remain);
    }

    // basket = 장바구니 인원, remain = 잔여좌석(총잔여인원)
    public void setTime(int basket, int remain) {
        // 이미 만석
        if (remain <= 0) {
            this.time = 0L;
            return;
        }
        // 장바구니가 좌석보다 적으면 오픈 직후엔 마감 안 됨
        if (basket < remain) {
            this.time = Long.MAX_VALUE;
            return;
        }

        // 간단 가정값(필요 시 숫자만 조정)
        final double alpha   = 0.70;  // 첫 러시에 시도하는 비율
        final double betaSec = 3.0;   // 초기 러시 시간(초)
        final double gamma   = 1.00;  // 즉시 성공률

        double arrivalPerSec = (basket * alpha * gamma) / betaSec; // 초당 유효 신청량
        if (arrivalPerSec <= 0) {
            this.time = Long.MAX_VALUE;
            return;
        }

        long seconds = (long) Math.ceil(remain / arrivalPerSec);
        this.time = Math.max(1L, seconds) + 1L;
    }

}
