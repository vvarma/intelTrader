package com.inteltrader.service;

import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;
import com.inteltrader.util.IConstants;
import com.inteltrader.util.RestCodes;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/2/13
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface InstrumentService  {

    Instrument retrieveInstrument(String symbolName) throws NoSuchFieldException;
    RestCodes createInstrument(String symbolName,Calendar startDate) throws NoSuchFieldException;
    RestCodes updateInstruments(String portfolioName);


    List<Price> getNewPrices(String symbolName, Price currentPrice) throws NoSuchFieldException;
}
