package com.inteltrader.service;

import com.inteltrader.advisor.qlearningadvisor.Holdings;
import com.inteltrader.dao.IPortfolioDao;
import com.inteltrader.entity.*;
import com.inteltrader.util.RestCodes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/17/13
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
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
    private Logger logger=Logger.getLogger(this.getClass());

    @Override
    public RestCodes updatePortfolio(String portfolioName) throws IOException, NoSuchFieldException {   logger.debug("Updating Portfolio..");
        String[] token={"MACD","RSI","BBAND"};
       Portfolio portfolio = portfolioDao.retrievePortfolio(entityManager, portfolioName);
        for (Investment investment : portfolio.getInvestmentList()) {
            Holdings.HoldingState hState = investment.setCurrentPrice(getCurrentInstrumentPrice(investment.getSymbolName()));
            logger.debug("Updating Investment :"+investment.getSymbolName()+investment.getCurrentPrice().getClosePrice());
            investmentService.makeInvestment(analyserService.getAnalysis(investment.getSymbolName(),token), investment);

        }
        logger.debug("Updating portfolio dao..");
        portfolioDao.updatePortfolio(entityManager, portfolio);
        return RestCodes.SUCCESS;

    }

    @Override
    public RestCodes createPortfolio(String portfolioName) {
        try {
            Portfolio portfolio = new Portfolio(portfolioName);
            portfolioDao.createPortfolio(entityManager, portfolio);
            return RestCodes.SUCCESS;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return RestCodes.FAILURE;
        }

    }

    @Override
    public RestCodes addToPortfolio(String portfolioName, String symbolName) throws NoSuchFieldException {
        String[] token={"MACD","RSI","BBAND"};
        try {
            Investment investment = new Investment(symbolName);
            investment.setCurrentPrice(getCurrentInstrumentPrice(symbolName));
            investmentService.makeInvestment(analyserService.getAnalysis(symbolName, token), investment);
            Portfolio portfolio = portfolioDao.retrievePortfolio(entityManager, portfolioName);
            System.out.println(portfolio);
            if (!portfolio.getInvestmentList().contains(investment))
                portfolio.getInvestmentList().add(investment);
            investment.setAssociatedPortfolio(portfolio);

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
            double invested = 0.0;
            double value=0.0;
            for (Transactions transactions : investment.getTransactionsList()) {
                invested += transactions.getQuantity() * transactions.getTransactionPrice().getClosePrice();
            }
            value+=investment.getQuantity()*investment.getCurrentPrice().getClosePrice();
            pnl+=(value-invested);
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
