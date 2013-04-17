package com.inteltrader.service;

import com.inteltrader.entity.Investment;
import com.inteltrader.entity.Portfolio;
import com.inteltrader.util.RestCodes;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 2/4/13
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PortfolioService {
    void updatePortfolio();
    RestCodes createPortfolio(String portfolioName);
    void addToPortfolio(Investment investment);
    List<Investment> retrieveInvestments(Portfolio portfolio);
    Double calculatePnL(Portfolio portfolio);
}
