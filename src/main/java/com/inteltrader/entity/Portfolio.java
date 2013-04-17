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
@Table(name="PORTFOLIO",schema = "TRADER_DB")
public class Portfolio  implements Serializable {
    @Id
    @Column(name="PORTFOLIO_NAME")
    private String portfolioName;
    @ElementCollection(targetClass = com.inteltrader.entity.Investment.class)
    @JoinTable(
            name="PORTFOLIO_INVESTMENT",
            schema="TRADER_DB",
            joinColumns=@JoinColumn(name="PORTFOLIO_NAME")
    )
    private List<Investment> investmentList=new ArrayList<Investment>();

    public Portfolio(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public Portfolio() {
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public List<Investment> getInvestmentList() {
        return investmentList;
    }

    public void setInvestmentList(List<Investment> investmentList) {
        this.investmentList = investmentList;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioName='" + portfolioName + '\'' +
                ", investmentList=" + investmentList +
                '}';
    }
}
