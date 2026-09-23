package com.satya.domain.customer.rewards.program.controller;

import com.satya.domain.customer.rewards.program.dto.RewardsResponse;
import com.satya.domain.customer.rewards.program.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rewards")
public class RewardsController {

    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<RewardsResponse> getCustomerRewards(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(rewardsService.getRewardPoints(customerId));
    }


    @GetMapping("/customers")
    public ResponseEntity<List<RewardsResponse>> getAllCustomerRewards() {
        return ResponseEntity.ok().body(rewardsService.getAllRewardPoints());
    }


}
