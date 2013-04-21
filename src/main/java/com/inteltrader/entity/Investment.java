package com.inteltrader.entity;

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
@Table(name = "INVESTMENT",schema = "TRADER_DB")
public class Investment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INVESTMENT_ID")
    private int investmentId=1;
    @Column(name = "INVESTMENT_SYMBOL")
    private String symbolName;
    private Integer quantity;
    private Double currentPrice;
    @ManyToOne (cascade = CascadeType.ALL,fetch = FetchType.EAGER,targetEntity = com.inteltrader.entity.Portfolio.class)
    @JoinColumn(name = "INVESTMENT_PORTFOLIO", nullable=false, updatable=false)
    private Portfolio associatedPortfolio;
    @ElementCollection(targetClass = com.inteltrader.entity.Transactions.class)
    @JoinTable(
            name="INVESTMENT_TRANSACTION",
            schema="TRADER_DB",
            joinColumns=@JoinColumn(name="INVESTMENT_ID")
    )
    private List<Transactions> transactionsList=new ArrayList<Transactions>();
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

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
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
