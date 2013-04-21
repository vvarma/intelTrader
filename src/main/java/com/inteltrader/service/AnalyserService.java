package com.inteltrader.service;

import com.inteltrader.advisor.Advice;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 5/4/13
 * Time: 11:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AnalyserService {
    Advice getAnalysis(String symbolName);
}
