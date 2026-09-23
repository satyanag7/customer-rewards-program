package com.satya.domain.customer.rewards.program.service;

import com.satya.domain.customer.rewards.program.dto.MonthlyRewards;
import com.satya.domain.customer.rewards.program.dto.RewardsResponse;
import com.satya.domain.customer.rewards.program.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RewardsService {

    private final TransactionDataSet transactionDataSet;

    RewardsService(TransactionDataSet transactionDataSet) {
        this.transactionDataSet = transactionDataSet;
    }

   public RewardsResponse getRewardPoints(long id) {

        List<Transaction> customerTransaction = transactionDataSet.getTransactions().stream().
                filter(transaction -> transaction.getCustomerId() == id).toList();

       for(Transaction tx : customerTransaction ) {
           System.out.println("Customer Id: " + tx.getCustomerId() + "Customer Transaction: " + tx.getTransactionId() + " Amount: " + tx.getAmount());
       }

        if (customerTransaction.isEmpty()) {
            return null;
        } else {
            RewardsResponse rewardsResponse = new RewardsResponse();
            rewardsResponse.setCustomerId(id);
            List<MonthlyRewards> monthlyRewardsList = new ArrayList<>();
            int totalRewardPoints = 0;

            for (Transaction transaction : customerTransaction) {
                int rewardPoints = calculateRewardPoints(transaction.getAmount());

                if(rewardPoints>0) {
                    totalRewardPoints += rewardPoints;

                    MonthlyRewards monthlyRewards = new MonthlyRewards();
                    monthlyRewards.setMonth(transaction.getTransactionDate().getMonth().toString());
                    monthlyRewards.setRewardPoints(rewardPoints);
                    monthlyRewardsList.add(monthlyRewards);
                }
            }

            rewardsResponse.setMonthlyRewards(monthlyRewardsList);
            rewardsResponse.setTotalRewardPoints(totalRewardPoints);
            return rewardsResponse;
       }

    }

    private int calculateRewardPoints(Double amount) {

        int countPointsPerTx = 0;
        if (amount > 100) {
            countPointsPerTx = (int) (2 * (amount - 100));
        }
        if (amount > 50) {
            countPointsPerTx += (int) (50);
        }
        return countPointsPerTx;
    }

    public List<RewardsResponse> getAllRewardPoints() {

         List<Transaction> allTransactions = transactionDataSet.getTransactions();
         List<RewardsResponse> allRewardsResponses = new ArrayList<>();

         // Group transactions by customerId
         allTransactions.stream()
                 .map(Transaction::getCustomerId)
                 .distinct()
                 .forEach(customerId -> {
                     RewardsResponse rewardsResponse = getRewardPoints(customerId);
                     if (rewardsResponse != null) {
                         allRewardsResponses.add(rewardsResponse);
                     }
                 });

         return allRewardsResponses;
     }
}
