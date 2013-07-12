package com.inteltrader.entity.view;

import com.inteltrader.entity.Investment;
import com.inteltrader.entity.Portfolio;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 7/8/13
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class PortfolioVo {
    private String portfolioName;
    private Set<InvestmentVo> investmentList=new HashSet<InvestmentVo>();

    public PortfolioVo(String portfolioName) {
        this.portfolioName=portfolioName;

    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public Set<InvestmentVo> getInvestmentList() {
        return investmentList;
    }

    public PortfolioVo(Portfolio portfolio) {
        portfolioName =portfolio.getPortfolioName();
        for (Investment investment:portfolio.getInvestmentList()){
            investmentList.add(new InvestmentVo(investment));
        }
    }
}
