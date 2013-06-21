package com.inteltrader.entity;




import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
@Table(name="PORTFOLIO",schema = "TRADER_DB")
public class Portfolio  implements Serializable {
    @Id
    @Column(name="PORTFOLIO_NAME")
    private String portfolioName;
    @OneToMany(mappedBy = "associatedPortfolio",cascade = CascadeType.ALL,targetEntity = com.inteltrader.entity.Investment.class)
    private Set<Investment> investmentList=new HashSet<Investment>();

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
                ", investmentList=" + investmentList +
                '}';
    }


}
