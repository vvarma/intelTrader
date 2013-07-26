package com.inteltrader.dao;

import com.inteltrader.entity.Instrument;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/16/13
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Transactional(propagation = Propagation.MANDATORY)
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
        entityManager.merge(instrument);
    }

    @Override
    public void deleteInstrument(EntityManager entityManager, Instrument instrument) {
        entityManager.remove(instrument);
    }


}
