package com.inteltrader.entity.view;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.qlearning.State;
import com.inteltrader.entity.States;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 8/6/13
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatesVo {
    Set<StateVo> stateVoSet;
    StateVo presentState;
    Advice presentAdvice;

    public StatesVo(States states) {
        stateVoSet = new HashSet<StateVo>();
        for (State s : states.getStateSet()) {
            stateVoSet.add(new StateVo(s));
        }
        presentState = new StateVo(states.getPresentState());
        presentAdvice = states.getPresentAdvice();
    }
}
