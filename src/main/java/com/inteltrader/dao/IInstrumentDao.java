package com.inteltrader.dao;

import com.inteltrader.entity.Instrument;

import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/15/13
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IInstrumentDao {
    void createInstrument(Instrument instrument);
    Instrument retrieveInstrument(String symbolName) throws NoSuchFieldException;
    void updateInstrument(Instrument instrument);
    void deleteInstrument(Instrument instrument);

}
