package com.yosep.batch.project1;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderStatistics {
    private String amount;

    private LocalDateTime localDateTime;

    @Builder
    private OrderStatistics(String amount, LocalDateTime localDateTime) {
        this.amount = amount;
        this.localDateTime = localDateTime;
    }
}
