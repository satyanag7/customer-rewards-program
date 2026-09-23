package com.satya.domain.customer.rewards.program.dto;

import lombok.Lombok;

import java.util.List;
import java.util.Map;

@lombok.Setter
@lombok.Getter
public class RewardsResponse {

    private long customerId;
    List<MonthlyRewards> monthlyRewards;
    private int totalRewardPoints;
}
