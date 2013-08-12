package com.inteltrader.advisor;

import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;
import com.inteltrader.entity.States;

import java.io.IOException;

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
    void initAdvisor(Instrument instrument,States states, String... token);
    InstrumentWrapper getWrapper();
    //no reward as of now... meaning no online trainingS
}
