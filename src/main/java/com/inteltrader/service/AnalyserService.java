package com.inteltrader.service;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.qlearningadvisor.Holdings;
import com.inteltrader.advisor.tawrapper.InstrumentWrapper;
import com.inteltrader.advisor.tawrapper.TAWrapper;

import javax.persistence.EntityManager;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 5/4/13
 * Time: 11:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AnalyserService {
    Advice getAnalysis(String symbolName,EntityManager entityManager);
    InstrumentWrapper getWrapper(String symbolName,String[] tokens) throws IOException, NoSuchFieldException;
    void createAnalyser(String symbolName, EntityManager entityManager, Holdings.HoldingState hState) throws IOException, NoSuchFieldException;
}
