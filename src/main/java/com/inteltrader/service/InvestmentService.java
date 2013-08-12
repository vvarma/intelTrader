package com.inteltrader.service;

import com.inteltrader.advisor.Advice;
import com.inteltrader.entity.Investment;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 21/4/13
 * Time: 9:46 AM
 * To change this template use File | Settings | File Templates.
 */
public interface InvestmentService {
    void makeInvestment(Advice advice,Investment investment);
    double calcPnl();
}
