package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.InstrumentAo;
import com.inteltrader.advisor.qlearningadvisor.State;
import com.inteltrader.entity.Price;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/27/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public interface InstrumentWrapper {
    public InstrumentAo getInstrument();
    public State.Builder updateWrapperAndGetStateBuilder(Price price, State presentState, Advice presentAdvice)throws IOException;
    public State.Builder getStateBuilder()throws IOException;
    public void updateHoldings(Advice advice);

}
