package com.inteltrader.advisor;

import com.inteltrader.advisor.qlearningadvisor.State;
import com.inteltrader.advisor.tawrapper.InstrumentWrapper;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;
import com.inteltrader.entity.States;

import java.io.IOException;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 1/5/13
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
 public interface Advisor {
    Advice getAdvice();
    Advice updatePriceAndGetAdvice(Price price) throws IOException;
    States getStates();
    void initAdvisor(Instrument instrument, String... token) throws IOException, InstantiationException;
    InstrumentWrapper getWrapper();
    //no reward as of now... meaning no online trainingS
}
