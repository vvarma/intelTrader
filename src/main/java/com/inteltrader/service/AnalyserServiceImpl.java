package com.inteltrader.service;

import com.inteltrader.advisor.Advice;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 21/4/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class AnalyserServiceImpl implements AnalyserService {
    @Override
    public Advice getAnalysis(String symbolName) {
        return Advice.BUY;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
