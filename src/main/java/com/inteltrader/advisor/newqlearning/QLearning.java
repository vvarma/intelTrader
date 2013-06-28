package com.inteltrader.advisor.newqlearning;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.Advisor;
import com.inteltrader.advisor.qlearningadvisor.Holdings;
import com.inteltrader.advisor.tawrapper.InstrumentWrapper;
import com.inteltrader.advisor.tawrapper.TAWrapper;
import com.inteltrader.dao.IStatesDao;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;
import com.inteltrader.entity.States;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 6/28/13
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class QLearning implements Advisor {
    @Autowired
    private IStatesDao statesDao;
    private States states;
    private InstrumentWrapper wrapper;

    public void initWrapper(Instrument instrument,String... token) throws IOException{
        wrapper= TAWrapper.WrapMaker(instrument,token);
    }
    public void initStates(){
        states=statesDao.retrieveStates(wrapper.getInstrument().getSymbolName());
        if (states==null){

        }
    }

    public class Trainer{
        private InstrumentWrapper wrapper;
        private Holdings holdings;

    }

    @Override
    public Advice getAdvice() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Advice updatePriceAndGetAdvice(Price price) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public States getStates() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
