package com.inteltrader.service;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.Advisor;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 21/4/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class AnalyserServiceImpl implements AnalyserService {
    private Advisor advisor;
    @Override
    public Advice getAnalysis(String symbolName) {
        return advisor.getAdvice();
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }
}
