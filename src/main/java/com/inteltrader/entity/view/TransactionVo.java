package com.inteltrader.entity.view;

import com.inteltrader.entity.Transactions;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 7/8/13
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionVo {
    private Long transactionId;
    private Integer quantity;
    private PriceVo transactionPrice;
    private String transactionDate;

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PriceVo getTransactionPrice() {
        return transactionPrice;
    }

    public TransactionVo(Transactions transaction) {
        transactionId = transaction.getTransactionId();
        quantity = transaction.getQuantity();
        transactionPrice = new PriceVo(transaction.getTransactionPrice());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        transactionDate = sdf.format(transaction.getTransactionDate());

    }
}
