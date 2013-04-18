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
    RestCodes updatePortfolio(String portfolioName);
    RestCodes createPortfolio(String portfolioName);
    RestCodes addToPortfolio(String portfolioName, String symbolName);
    Portfolio retrievePortfolio(String portfolioName);
    Double calculatePnL(String portfolioName);
}
