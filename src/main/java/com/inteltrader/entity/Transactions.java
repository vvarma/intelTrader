package com.inteltrader.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/2/13
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class Transactions implements Serializable {

    private Long transactionId;
    private Integer quantity;
    private Price transactionPrice;
    private Date transactionDate;

    public Transactions(Integer quantity, Price transactionPrice, Date transactionDate) {
        this.quantity = quantity;
        this.transactionPrice = transactionPrice;
        this.transactionDate = transactionDate;
    }


    public Transactions() {
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Price getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(Price transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "transactionId=" + transactionId +
                ", quantity=" + quantity +
                ", transactionPrice=" + transactionPrice +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
