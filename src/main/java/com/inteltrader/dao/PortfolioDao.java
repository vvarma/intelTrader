package com.inteltrader.dao;

import com.inteltrader.entity.Portfolio;

import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/17/13
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class PortfolioDao implements IPortfolioDao {
    @Override
    public void createPortfolio(EntityManager entityManager, Portfolio portfolio) {
        entityManager.persist(portfolio);
    }

    @Override
    public void updatePortfolio(EntityManager entityManager, Portfolio portfolio) {
        entityManager.getTransaction().begin();
        entityManager.merge(portfolio);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deletePortfolio(EntityManager entityManager, Portfolio portfolio) {
        entityManager.remove(portfolio);
    }

    @Override
    public Portfolio retrievePortfolio(EntityManager entityManager, String portfolioName) {
        Portfolio portfolio=entityManager.find(Portfolio.class,portfolioName);
        return portfolio;
    }
}
