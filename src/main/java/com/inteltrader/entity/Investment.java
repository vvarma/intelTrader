package com.inteltrader.entity;

import com.inteltrader.advisor.qlearning.Holdings;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/2/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Entity
@Table(name = "INVESTMENT")
public class Investment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INVESTMENT_ID")
    private int investmentId = 1;
    @Column(name = "INVESTMENT_SYMBOL")
    private String symbolName;
    private Integer quantity = 0;
    private Price currentPrice;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = com.inteltrader.entity.Portfolio.class)
    @JoinColumn(name = "INVESTMENT_PORTFOLIO", nullable = false, updatable = false)
    private Portfolio associatedPortfolio;
    @ElementCollection(targetClass = com.inteltrader.entity.Transactions.class)
    @JoinTable(
            name = "INVESTMENT_TRANSACTION",
            joinColumns = @JoinColumn(name = "INVESTMENT_ID")
    )
    private List<Transactions> transactionsList = new ArrayList<Transactions>();

    public Investment(String symbolName) {
        this.symbolName = symbolName;
    }

    public Investment() {

    }

    @Override
    public String toString() {
        return "Investment{" +
                "symbolName='" + symbolName + '\'' +
                ", quantity=" + quantity +
                ", currentPrice=" + currentPrice +
                ", transactionsList=" + transactionsList +
                '}';
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Price getCurrentPrice() {
        return currentPrice;
    }

    public Holdings.HoldingState setCurrentPrice(Price currentPrice) {
        Holdings.HoldingState toBeReturned = null;
        if (quantity == 0) {
            this.currentPrice = currentPrice;
            return Holdings.HoldingState.NO_HOLDING;
        }
        if (this.currentPrice.getClosePrice() < currentPrice.getClosePrice()) {
            if (quantity > 0) {
                toBeReturned = Holdings.HoldingState.LONG_PROFIT;
            } else {
                toBeReturned = Holdings.HoldingState.SHORT_LOSS;
            }
        } else {
            if (quantity > 0) {
                toBeReturned = Holdings.HoldingState.LONG_LOSS;
            } else {
                toBeReturned = Holdings.HoldingState.SHORT_PROFIT;
            }

        }

        this.currentPrice = currentPrice;
        return toBeReturned;
    }

    public List<Transactions> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public int getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(int investmentId) {
        this.investmentId = investmentId;
    }

    public Portfolio getAssociatedPortfolio() {
        return associatedPortfolio;
    }

    public void setAssociatedPortfolio(Portfolio associatedPortfolio) {
        this.associatedPortfolio = associatedPortfolio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Investment)) return false;

        Investment that = (Investment) o;

        if (!associatedPortfolio.equals(that.associatedPortfolio)) return false;
        if (!symbolName.equals(that.symbolName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = symbolName.hashCode();
        result = 31 * result + associatedPortfolio.hashCode();
        return result;
    }

    public double calcPnl() {
        double pnl = 0.0;
        double invested = 0.0;
        double value = 0.0;
        for (Transactions transactions : this.getTransactionsList()) {
            invested += transactions.getQuantity() * transactions.getTransactionPrice().getClosePrice();
        }
        value += this.getQuantity() * this.getCurrentPrice().getClosePrice();
        pnl += (value - invested);
        return pnl;
    }
/*

    @Override
    public String toString() {
        return "Investment{" +
                "investmentId=" + investmentId +
                ", symbolName='" + symbolName + '\'' +
                ", quantity=" + quantity +
                ", currentPrice=" + currentPrice +
                '}';

    }*/
}
