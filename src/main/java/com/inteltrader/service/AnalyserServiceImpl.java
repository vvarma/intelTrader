package com.inteltrader.service;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.Advisor;
import com.inteltrader.advisor.qlearningadvisor.Holdings;
import com.inteltrader.advisor.qlearningadvisor.QLearningAdvisorImpl2;
import com.inteltrader.advisor.tawrapper.InstrumentWrapper;
import com.inteltrader.advisor.tawrapper.TAWrapper;
import com.inteltrader.dao.IStatesDao;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.States;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
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

    @Autowired
    private Advisor advisor;
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private IStatesDao statesDao;
    private Logger logger=Logger.getLogger(this.getClass());

    @Override
    public Advice getAnalysis(String symbolName, EntityManager entityManager) {
        States states=statesDao.retrieveStates(symbolName);
        logger.debug("Get Analysis .. present Advice :" +states.getPresentAdvice());
        return states.getPresentAdvice();
    }

    @Override
    public void createAnalyser(String symbolName, EntityManager entityManager, Holdings.HoldingState hState) throws IOException, NoSuchFieldException {
        logger.debug("Creating Analyser for symbol "+symbolName+" and HState +"+hState);
        Instrument instrument=instrumentService.retrieveInstrument(symbolName);
        States states=statesDao.retrieveStates(symbolName);
        if (states==null){
            logger.debug("Creating Advisor first time..");
            advisor=new QLearningAdvisorImpl2(instrument,hState,"MACD","RSI");

        }else{
            logger.debug("States exist, creating advisor from retrieved states");
           advisor=new QLearningAdvisorImpl2(states,instrument,hState,"MACD","RSI");
        }
        states=advisor.getStates();
        logger.debug("Saving states to db..");
        logger.debug("Present State is :"+states.getPresentState() +'\n'+
        "Present Advice is :"+states.getPresentAdvice() );
        //logger.debug(states);
        statesDao.createState(states);

    }

    @Override
    public InstrumentWrapper getWrapper(String symbolName,String[] tokens) throws IOException, NoSuchFieldException {
        Instrument instrument=instrumentService.retrieveInstrument(symbolName);

        InstrumentWrapper taWrapper=TAWrapper.WrapMaker(instrument,tokens);
        return taWrapper;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public InstrumentService getInstrumentService() {
        return instrumentService;
    }

    public void setInstrumentService(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }
}
