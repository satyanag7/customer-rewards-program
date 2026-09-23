package com.satya.domain.customer.rewards.program.service;

import com.satya.domain.customer.rewards.program.dto.MonthlyRewards;
import com.satya.domain.customer.rewards.program.dto.RewardsResponse;
import com.satya.domain.customer.rewards.program.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardsServiceTest {

    @Mock
    private TransactionDataSet transactionDataSet;

    @InjectMocks
    private RewardsService rewardsService;

    private Transaction tx1;
    private Transaction tx2;
    private Transaction tx3;

    @BeforeEach
    void setUp() {
        // Mocking individual transactions
        tx1 = new Transaction(1L, 101L, "MIC", LocalDate.of(2026, 1, 15), 120.0); // JANUARY
        tx1.setCustomerId(1L);
        tx1.setTransactionId(101L);
        tx1.setAmount(120.0); // Expects (2*20) + (1*50) = 90 points
        tx1.setTransactionDate(LocalDate.of(2026, 1, 15)); // JANUARY

        tx2 = new Transaction(2L, 102L, "TV", LocalDate.of(2026, 2, 20),80.0); // FEBRUARY
        tx2.setCustomerId(1L);
        tx2.setTransactionId(102L);
        tx2.setAmount(80.0);  // Expects (1*30) = 30 points
        tx2.setTransactionDate(LocalDate.of(2026, 2, 20)); // FEBRUARY

        tx3 = new Transaction(3L, 103L, "Phone", LocalDate.of(2026, 3, 10), 40.0); // MARCH
        tx3.setCustomerId(2L); // Different customer
        tx3.setTransactionId(103L);
        tx3.setAmount(40.0);  // Expects 0 points
        tx3.setTransactionDate(LocalDate.of(2026, 3, 10)); // MARCH
    }

    @Test
    void testGetRewardPoints_Success() {
        // Arrange
        long customerId = 1L;
        List<Transaction> transactions = Arrays.asList(tx1, tx2, tx3);
        when(transactionDataSet.getTransactions()).thenReturn(transactions);

        // Act
        RewardsResponse response = rewardsService.getRewardPoints(customerId);

        // Assert
        assertNotNull(response);
        assertEquals(customerId, response.getCustomerId());

        List<MonthlyRewards> monthlyRewards = response.getMonthlyRewards();
        assertEquals(2, monthlyRewards.size());

        // Validate first transaction rewards (JANUARY)
        MonthlyRewards janRewards = monthlyRewards.get(0);
        assertEquals("JANUARY", janRewards.getMonth());
        assertEquals(90, janRewards.getRewardPoints());

        // Validate second transaction rewards (FEBRUARY)
        MonthlyRewards febRewards = monthlyRewards.get(1);
        assertEquals("FEBRUARY", febRewards.getMonth());
        assertEquals(50, febRewards.getRewardPoints());
    }

    @Test
    void testGetRewardPoints_NoTransactionsFound() {
        // Arrange
        long nonExistentCustomerId = 99L;
        when(transactionDataSet.getTransactions()).thenReturn(Collections.emptyList());

        // Act
        RewardsResponse response = rewardsService.getRewardPoints(nonExistentCustomerId);

        // Assert
        assertNull(response);
    }

    @Test
    void testGetRewardPoints_TransactionsExistButNoPointsEarned() {
        // Arrange
        long customerId = 2L;
        List<Transaction> transactions = Collections.singletonList(tx3);
        when(transactionDataSet.getTransactions()).thenReturn(transactions);

        // Act
        RewardsResponse response = rewardsService.getRewardPoints(customerId);

        // Assert
        assertNotNull(response);
        assertEquals(customerId, response.getCustomerId());
        // Since points for $40 is 0, the list should be empty based on your if(rewardPoints>0) check
        assertTrue(response.getMonthlyRewards().isEmpty());
    }
}

