package com.inteltrader.service;

import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Portfolio;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/2/13
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface InstrumentService {

    Instrument retrieveInstrument(String symbolName);
    Instrument retrieveInstrument(Long instrumentId);
    void createInstrument(String symbolName,Calendar startDate);
    void updateInstruments();
    void deleteInstrument(Instrument instrument);

}
