package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.InstrumentAo;
import com.inteltrader.entity.Price;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/27/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public interface InstrumentWrapper {
    public InstrumentAo getInstrument();
    public void updateWrapper(Price price);
}
