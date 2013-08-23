package com.inteltrader.service;

import com.inteltrader.entity.Portfolio;
import com.inteltrader.util.RestCodes;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 2/4/13
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PortfolioService {
    RestCodes updatePortfolio(String portfolioName) throws IOException, NoSuchFieldException, CloneNotSupportedException;

    RestCodes createPortfolio(String portfolioName, String desc);

    RestCodes addToPortfolio(String portfolioName, String symbolName) throws NoSuchFieldException, CloneNotSupportedException;

    Portfolio retrievePortfolio(String portfolioName) throws NoSuchFieldException;

    Double calculatePnL(String portfolioName) throws NoSuchFieldException;

    List<String> listAllPortfolios();

    Calendar lastUpdatedOn(String portfolioName) throws NoSuchFieldException;

    RestCodes updateAllPortfolio() throws IOException, NoSuchFieldException, CloneNotSupportedException;
}
