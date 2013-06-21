package com.inteltrader.dao;

import com.inteltrader.entity.States;

import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 6/17/13
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class StatesDao implements IStatesDao {
    @Override
    public void createState(States states,EntityManager entityManager) {
        entityManager.merge(states);
    }

    @Override
    public States retrieveStates(String symbolName, EntityManager entityManager) {
        return entityManager.find(States.class,symbolName);
    }
}
