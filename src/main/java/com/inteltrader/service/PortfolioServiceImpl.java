package com.inteltrader.service;

import com.inteltrader.dao.IPortfolioDao;
import com.inteltrader.entity.Investment;
import com.inteltrader.entity.Portfolio;
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

    @Override
    public void updatePortfolio() {
        //To change body of implemented methods use File | Settings | File Templates.
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
    public void addToPortfolio(Investment investment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Investment> retrieveInvestments(Portfolio portfolio) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Double calculatePnL(Portfolio portfolio) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
}
