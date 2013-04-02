package com.inteltrader.service;

import com.inteltrader.entity.Instrument;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/2/13
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface InstrumentService {
    Instrument loadInstrument(String symbolName);
    Instrument updateInstrument();

}
