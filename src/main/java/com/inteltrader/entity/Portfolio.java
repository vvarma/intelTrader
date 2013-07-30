package com.inteltrader.entity;


import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/2/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Entity
@Table(name = "PORTFOLIO", schema = "TRADER_DB")
public class Portfolio implements Serializable {
    @Id
    @Column(name = "PORTFOLIO_NAME")
    private String portfolioName;
    @Column(name = "STRATEGY")
    private String desc;
    @OneToMany(mappedBy = "associatedPortfolio", cascade = CascadeType.ALL, targetEntity = com.inteltrader.entity.Investment.class)
    private Set<Investment> investmentList = new HashSet<Investment>();
    @Column(name = "LAST_UPDATED_ON")
    private Calendar lastUpdatedOn;

    public Portfolio(String portfolioName, String desc, Calendar timestamp) {
        this.portfolioName = portfolioName;
        this.lastUpdatedOn = timestamp;
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Portfolio)) return false;

        Portfolio portfolio = (Portfolio) o;

        if (!desc.equals(portfolio.desc)) return false;
        if (!investmentList.equals(portfolio.investmentList)) return false;
        if (!portfolioName.equals(portfolio.portfolioName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = portfolioName.hashCode();
        result = 31 * result + desc.hashCode();
        result = 31 * result + investmentList.hashCode();
        return result;
    }

    public Calendar getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Calendar lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Portfolio() {

    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public Set<Investment> getInvestmentList() {
        return investmentList;
    }

    public void setInvestmentList(Set<Investment> investmentList) {
        this.investmentList = investmentList;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioName='" + portfolioName + '\'' +
                ", desc='" + desc + '\'' +
                ", investmentList=" + investmentList +
                '}';
    }


}
