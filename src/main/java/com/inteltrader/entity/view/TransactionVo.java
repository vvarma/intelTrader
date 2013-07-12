package com.inteltrader.entity.view;

import com.inteltrader.entity.Price;
import com.inteltrader.entity.Transactions;

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
        transactionId=transaction.getTransactionId();
        quantity=transaction.getQuantity();
        transactionPrice=new PriceVo(transaction.getTransactionPrice());
    }
}
