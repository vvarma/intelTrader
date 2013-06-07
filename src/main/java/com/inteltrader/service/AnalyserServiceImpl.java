package com.inteltrader.service;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.Advisor;
import com.inteltrader.advisor.qlearningadvisor.QLearningAdvisor;
import com.inteltrader.advisor.simpleadvisor.SimpleAdvisor;
import com.inteltrader.advisor.tawrapper.InstrumentWrapper;
import com.inteltrader.advisor.tawrapper.TAWrapper;
import com.inteltrader.entity.Instrument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 21/4/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class AnalyserServiceImpl implements AnalyserService {
    @Autowired
    InstrumentService instrumentService;
    private Advisor advisor;
    @Override
    public Advice getAnalysis(String symbolName) {
        Instrument instrument=instrumentService.retrieveInstrument(symbolName);
        try{
            //advisor= SimpleAdvisor.buildAdvisor(instrument,"MACD");
            if (advisor==null)
                advisor= new QLearningAdvisor(instrument);
            return advisor.getAdvice();
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public InstrumentWrapper getWrapper(String symbolName) throws IOException{
        Instrument instrument=instrumentService.retrieveInstrument(symbolName);
        InstrumentWrapper taWrapper=TAWrapper.WrapMaker(instrument,"MACD");
        return taWrapper;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public InstrumentService getInstrumentService() {
        return instrumentService;
    }

    public void setInstrumentService(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }
}
