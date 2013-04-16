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
    void createInstrument(EntityManager entityManager,Instrument instrument);
    Instrument retrieveInstrument(EntityManager entityManager,Long instrumentId);
    Instrument retrieveInstrument(EntityManager entityManager,String symbolName);
    void updateInstrument(EntityManager entityManager,Instrument instrument);
    void deleteInstrument(EntityManager entityManager,Instrument instrument);
}
