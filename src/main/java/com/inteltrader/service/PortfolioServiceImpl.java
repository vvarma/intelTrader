package com.inteltrader.service;

import com.inteltrader.advisor.Advice;
import com.inteltrader.dao.IPortfolioDao;
import com.inteltrader.entity.*;
import com.inteltrader.util.RestCodes;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/17/13
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class PortfolioServiceImpl implements PortfolioService {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private IPortfolioDao portfolioDao;
    @Autowired
    private AnalyserService analyserService;
    @Autowired
    private InvestmentService investmentService;
    @Autowired
    private InstrumentService instrumentService;

    @Override
    public RestCodes updatePortfolio(String portfolioName) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        Portfolio portfolio=portfolioDao.retrievePortfolio(entityManager,portfolioName);
            for (Investment investment:portfolio.getInvestmentList()){
            investment.setCurrentPrice(getCurrentInstrumentPrice(investment.getSymbolName()));
            investmentService.makeInvestment(analyserService.getAnalysis(investment.getSymbolName()),investment);

        }
        entityManager.getTransaction().begin();
        portfolioDao.updatePortfolio(entityManager, portfolio);
        entityManager.getTransaction().commit();
        return RestCodes.SUCCESS;

    }

    @Override
    public RestCodes createPortfolio(String portfolioName) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try{
            Portfolio portfolio=new Portfolio(portfolioName);
            portfolioDao.createPortfolio(entityManager,portfolio);
            return RestCodes.SUCCESS;
        }catch (RuntimeException e){
             e.printStackTrace();
            return RestCodes.FAILURE;
        }finally {
            entityManager.getTransaction().commit();
        }

    }

     @Override
    public RestCodes addToPortfolio(String portfolioName,String symbolName) {
        try{
            Instrument instrument=instrumentService.retrieveInstrument(symbolName);
            EntityManager entityManager=entityManagerFactory.createEntityManager();
            Investment investment=new Investment(symbolName);
            investment.setCurrentPrice(instrument.getCurrentPrice());
            entityManager.getTransaction().begin();
            Portfolio portfolio=portfolioDao.retrievePortfolio(entityManager,portfolioName);
            System.out.println(portfolio);
            portfolio.getInvestmentList().add(investment);
            investment.setAssociatedPortfolio(portfolio);
            System.out.println(portfolio);
            //investmentService.makeInvestment(analyserService.getAnalysis(investment.getSymbolName()),investment);
            //portfolioDao.updatePortfolio(entityManager,portfolio);
            entityManager.getTransaction().commit();
            return RestCodes.SUCCESS;
        }catch (RuntimeException e){
            e.printStackTrace();
            return RestCodes.FAILURE;
        }
     }

    @Override
    public Portfolio retrievePortfolio(String portfolioName) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        Portfolio portfolio=portfolioDao.retrievePortfolio(entityManager,portfolioName);
       return portfolio;
    }

    @Override
    public Double calculatePnL(String portfolioName) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        Portfolio portfolio=portfolioDao.retrievePortfolio(entityManager,portfolioName);
        Double pnl=0.0;
        for(Investment investment:portfolio.getInvestmentList()){
            for(Transactions transactions: investment.getTransactionsList()){
                pnl+=transactions.getQuantity()*transactions.getTransactionPrice().getClosePrice();
            }
        }
        return pnl;
    }

    private Price getCurrentInstrumentPrice(String symbolName){
        return instrumentService.retrieveInstrument(symbolName).getCurrentPrice();

    }
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
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
