package com.inteltrader.dao;

import com.inteltrader.entity.States;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 6/17/13
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IStatesDao {
    void createState(States states);
    States retrieveStates(String symbolName);
}
