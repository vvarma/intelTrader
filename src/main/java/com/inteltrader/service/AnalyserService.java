package com.inteltrader.service;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.InstrumentWrapper;
import com.inteltrader.entity.States;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 5/4/13
 * Time: 11:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AnalyserService {
    Advice getAnalysis(String symbolName, String token) throws NoSuchFieldException;

    InstrumentWrapper getWrapper(String symbolName, String... tokens) throws IOException, NoSuchFieldException;

    void createAnalyser(String symbolName, String tokens) throws IOException, NoSuchFieldException;

    States getStates(String symbolName);
}
