package com.inteltrader.advisor;

import com.inteltrader.advisor.qlearning.State;
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
    public State.Builder getStateBuilder(int i) throws IndexOutOfBoundsException;
    public State.Builder updateWrapperAndGetStateBuilder(Price price) throws IOException;

}
