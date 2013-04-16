package com.inteltrader.service;

import com.inteltrader.entity.Portfolio;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 2/4/13
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class CronService {
    private InstrumentService instrumentService;
    private PortfolioService portfolioService;
    public void CronInstruments(){
        instrumentService.updateInstruments();
        portfolioService.updatePortfolio();
    }
}
