package com.inteltrader.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 7/8/13
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class PortfolioTest {
    @Autowired
    IPortfolioDao portfolioDao;
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Test
    public void shouldRetrieveAllPortfolios(){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        List<String> portfolios=portfolioDao.retrieveAllPortfolios(entityManager);
        Assert.notEmpty(portfolios);
        System.out.println(portfolios);
    }
}
