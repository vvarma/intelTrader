package com.inteltrader.dao;

import com.inteltrader.entity.Instrument;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/16/13
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Transactional(propagation = Propagation.MANDATORY)
public class InstrumentDao implements IInstrumentDao {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public void createInstrument(Instrument instrument) {
        entityManager.persist(instrument);
    }

    @Override
    public Instrument retrieveInstrument(String symbolName) throws NoSuchFieldException {
        Instrument instrument=entityManager.find(Instrument.class,symbolName);
        if (instrument==null)
            throw new NoSuchFieldException(symbolName + " not found");
        return instrument;
    }

    @Override
    public void updateInstrument(Instrument instrument) {
        entityManager.merge(instrument);
    }

    @Override
    public void deleteInstrument(Instrument instrument) {
        entityManager.remove(instrument);
    }


}
