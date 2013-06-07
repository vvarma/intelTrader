package com.inteltrader.advisor.qlearningadvisor;

import com.inteltrader.advisor.Advice;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 6/4/13
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface States {
    Advice getGreedyAdvice();
    Advice getNonGreedyAdvice(int iter);


}
