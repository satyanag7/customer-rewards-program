package com.satya.domain.customer.rewards.program.model;
import lombok.Setter;

import java.time.LocalDate;


@lombok.Getter
@lombok.Setter
public class Transaction {

   private long transactionId;
   private long customerId;
   private String productName;
   private LocalDate transactionDate;
   private double Amount;

   public Transaction(long transactionId, long customerId, String productName, LocalDate transactionDate, double Amount) {
       this.transactionId = transactionId;
       this.customerId = customerId;
       this.productName = productName;
       this.transactionDate = transactionDate;
       this.Amount = Amount;
   }








}
