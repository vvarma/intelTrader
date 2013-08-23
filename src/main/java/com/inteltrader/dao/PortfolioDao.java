package com.inteltrader.dao;

import com.inteltrader.entity.Portfolio;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/17/13
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Transactional(propagation = Propagation.MANDATORY)
public class PortfolioDao implements IPortfolioDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void createPortfolio(Portfolio portfolio) {
        entityManager.persist(portfolio);

    }

    @Override
    public void updatePortfolio(Portfolio portfolio) {
        entityManager.merge(portfolio);
    }

    @Override
    public void deletePortfolio(Portfolio portfolio) {
        entityManager.remove(portfolio);
    }

    @Override
    public Portfolio retrievePortfolio(String portfolioName) throws NoSuchFieldException {
        Portfolio portfolio = entityManager.find(Portfolio.class, portfolioName);
        if (portfolio == null) {
            throw new NoSuchFieldException(portfolioName + " not found");
        }
        return portfolio;
    }

    @Override
    public List<String> retrieveAllPortfolios() {
        String query = "select p.portfolioName from Portfolio p";
        List<String> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
    }
}
