package com.inteltrader.service;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.Advisor;
import com.inteltrader.advisor.InstrumentWrapper;
import com.inteltrader.advisor.tawrapper.TAWrapper;
import com.inteltrader.dao.IStatesDao;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.States;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 21/4/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
@Transactional(propagation = Propagation.REQUIRED)
public class AnalyserServiceImpl implements AnalyserService {
    @Autowired
    InstrumentService instrumentService;
    @Autowired
    private Advisor advisor;
    @Autowired
    private IStatesDao statesDao;
    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public Advice getAnalysis(String symbolName, String token) throws NoSuchFieldException {
        States states = statesDao.retrieveStates(symbolName + "-" + token);
        Instrument instrument = instrumentService.retrieveInstrument(symbolName);
        advisor.initAdvisor(instrument, states, token);
        states = advisor.getStates();
        statesDao.createState(states);
        logger.debug("Get Analysis .. present Advice :" + states.getPresentAdvice());
        return states.getPresentAdvice();
    }

    @Override
    public void createAnalyser(String symbolName, String tokens) throws IOException, NoSuchFieldException {
        logger.debug("Creating Analyser for symbol ");
        Instrument instrument = instrumentService.retrieveInstrument(symbolName);
        States states = statesDao.retrieveStates(symbolName + "-" + tokens);
        advisor.initAdvisor(instrument, states, tokens);
        logger.debug("Saving states to db..");
        logger.debug("Present State is :" + states.getPresentState() + '\n' +
                "Present Advice is :" + states.getPresentAdvice());
        //logger.debug(states);
        statesDao.createState(states);

    }

    @Override
    public States getStates(String symbolName) {
        return statesDao.retrieveStates(symbolName);

    }

    @Override
    public InstrumentWrapper getWrapper(String symbolName, String... tokens) throws IOException, NoSuchFieldException {
        Instrument instrument = instrumentService.retrieveInstrument(symbolName);
        InstrumentWrapper taWrapper = TAWrapper.WrapMaker(instrument, tokens);
        return taWrapper;
    }

    public InstrumentService getInstrumentService() {
        return instrumentService;
    }

    public void setInstrumentService(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }
}
