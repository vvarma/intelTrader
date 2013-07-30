package com.inteltrader.service;

import com.inteltrader.advisor.qlearning.Holdings;
import com.inteltrader.dao.IPortfolioDao;
import com.inteltrader.entity.Investment;
import com.inteltrader.entity.Portfolio;
import com.inteltrader.entity.Price;
import com.inteltrader.util.Global;
import com.inteltrader.util.RestCodes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/17/13
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Transactional(propagation = Propagation.REQUIRED)
public class PortfolioServiceImpl implements PortfolioService {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private IPortfolioDao portfolioDao;
    @Autowired
    private AnalyserService analyserService;
    @Autowired
    private InvestmentService investmentService;
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private Global global;
    private Logger logger=Logger.getLogger(this.getClass());

    @Override
    public RestCodes updatePortfolio(String portfolioName) throws IOException, NoSuchFieldException {   logger.debug("Updating Portfolio..");
        Portfolio portfolio = portfolioDao.retrievePortfolio(entityManager, portfolioName);
        String[] token=portfolio.getDesc().split("-");
        for (Investment investment : portfolio.getInvestmentList()) {
            Holdings.HoldingState hState = investment.setCurrentPrice(getCurrentInstrumentPrice(investment.getSymbolName()));
            logger.debug("Updating Investment :"+investment.getSymbolName()+investment.getCurrentPrice().getClosePrice());
            investmentService.makeInvestment(analyserService.getAnalysis(investment.getSymbolName(),token), investment);

        }
        logger.debug("Updating portfolio dao..");
        portfolio.setLastUpdatedOn(global.getCalendar());
        portfolioDao.updatePortfolio(entityManager, portfolio);
        return RestCodes.SUCCESS;

    }

    @Override
    public RestCodes createPortfolio(String portfolioName, String desc) {
        try {
            Portfolio portfolio = new Portfolio(portfolioName, desc,global.getCalendar());
            portfolioDao.createPortfolio(entityManager, portfolio);
            return RestCodes.SUCCESS;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return RestCodes.FAILURE;
        }
    }



    @Override
    public RestCodes addToPortfolio(String portfolioName, String symbolName) throws NoSuchFieldException {

        try {
            Portfolio portfolio = portfolioDao.retrievePortfolio(entityManager, portfolioName);
            String[] token=portfolio.getDesc().split("-");
            Investment investment = new Investment(symbolName);
            investment.setCurrentPrice(getCurrentInstrumentPrice(symbolName));
            investmentService.makeInvestment(analyserService.getAnalysis(symbolName, token), investment);

            if (!portfolio.getInvestmentList().contains(investment))
                portfolio.getInvestmentList().add(investment);
            investment.setAssociatedPortfolio(portfolio);
            portfolio.setLastUpdatedOn(global.getCalendar());
            return RestCodes.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return RestCodes.FAILURE;
        }
    }

    @Override
    public Portfolio retrievePortfolio(String portfolioName) {
        Portfolio portfolio = portfolioDao.retrievePortfolio(entityManager, portfolioName);

        return portfolio;
    }

    @Override
    public Double calculatePnL(String portfolioName) {
       Portfolio portfolio = portfolioDao.retrievePortfolio(entityManager, portfolioName);
        double pnl=0.0;
        for (Investment investment : portfolio.getInvestmentList()) {
            pnl+=investment.calcPnl();
        }
        return pnl;
    }

    @Override
    public List<String> listAllPortfolios() {
        try{
            return portfolioDao.retrieveAllPortfolios(entityManager);
        }    finally {
        }


    }

    @Override
    public Calendar lastUpdatedOn(String portfolioName) {
        Portfolio portfolio=retrievePortfolio(portfolioName);
        return portfolio.getLastUpdatedOn();
    }

    private Price getCurrentInstrumentPrice(String symbolName) throws NoSuchFieldException {
        Price price=instrumentService.retrieveInstrument(symbolName).getCurrentPrice();
        logger.fatal("Current Price is :" +price.getClosePrice());
        return price;

    }



    public IPortfolioDao getPortfolioDao() {
        return portfolioDao;
    }

    public void setPortfolioDao(IPortfolioDao portfolioDao) {
        this.portfolioDao = portfolioDao;
    }

    public AnalyserService getAnalyserService() {
        return analyserService;
    }

    public void setAnalyserService(AnalyserService analyserService) {
        this.analyserService = analyserService;
    }

    public InvestmentService getInvestmentService() {
        return investmentService;
    }

    public void setInvestmentService(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    public InstrumentService getInstrumentService() {
        return instrumentService;
    }

    public void setInstrumentService(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }
}
