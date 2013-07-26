package com.inteltrader.advisor.qlearning;

import javax.persistence.Embeddable;
import java.io.Serializable;

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
    private Double transactionPrice;

    public Transactions() {
    }

    public Transactions(Integer quantity, Double transactionPrice) {
        this.quantity = quantity;
        this.transactionPrice = transactionPrice;
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

    public Double getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(Double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "transactionId=" + transactionId +
                ", quantity=" + quantity +
                ", transactionPrice=" + transactionPrice +
                '}';
    }
}
