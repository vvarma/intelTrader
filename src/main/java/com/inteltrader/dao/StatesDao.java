package com.inteltrader.dao;

import com.inteltrader.entity.States;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 6/17/13
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class StatesDao implements IStatesDao {
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;
    @Override
    public void createState(States states) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(states);
        entityManager.getTransaction().commit();
    }

    @Override
    public States retrieveStates(String symbolName) {
        System.out.println("here1");
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        return entityManager.find(States.class,symbolName);
    }
}
