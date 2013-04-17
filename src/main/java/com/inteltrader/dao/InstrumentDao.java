package com.inteltrader.dao;

import com.inteltrader.entity.Instrument;

import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/16/13
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class InstrumentDao implements IInstrumentDao {
    @Override
    public void createInstrument(EntityManager entityManager, Instrument instrument) {
        entityManager.persist(instrument);
    }

    @Override
    public Instrument retrieveInstrument(EntityManager entityManager, String symbolName) {
        Instrument instrument=entityManager.find(Instrument.class,symbolName);
        return instrument;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateInstrument(EntityManager entityManager, Instrument instrument) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteInstrument(EntityManager entityManager, Instrument instrument) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
