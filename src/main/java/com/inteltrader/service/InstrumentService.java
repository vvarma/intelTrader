package com.inteltrader.service;

import com.inteltrader.entity.Instrument;
<<<<<<< HEAD
import com.inteltrader.util.RestCodes;
=======
import com.inteltrader.entity.Portfolio;
>>>>>>> 34b833fc0e5613f603b76f0b26320161f4b45c97

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
<<<<<<< HEAD
    void updateInstrument(Instrument instrument);
    RestCodes createInstrument(String symbolName,Calendar startDate);
=======
    void createInstrument(String symbolName,Calendar startDate);
    void updateInstruments();
    void deleteInstrument(Instrument instrument);
>>>>>>> 34b833fc0e5613f603b76f0b26320161f4b45c97

}
