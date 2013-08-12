package com.inteltrader.dao;

import com.inteltrader.entity.Portfolio;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/17/13
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPortfolioDao {
    void createPortfolio(Portfolio portfolio);
    void updatePortfolio(Portfolio portfolio);
    void deletePortfolio(Portfolio portfolio);
    Portfolio retrievePortfolio(String portfolioName) throws NoSuchFieldException;
    List<String> retrieveAllPortfolios();
}
