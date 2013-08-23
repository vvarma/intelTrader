package com.inteltrader.entity.view;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.qlearning.State;
import com.inteltrader.indicators.CalculatorBollingerBands;
import com.inteltrader.indicators.CalculatorMACD;
import com.inteltrader.indicators.CalculatorRSI;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 8/6/13
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateVo {
    CalculatorMACD.MACDState macdState;
    CalculatorRSI.RSIState rsiState;
    CalculatorBollingerBands.BBandState bBandState;
    Advice greedyAdvice;

    public StateVo(State s) {
        macdState = s.getMacdState();
        rsiState = s.getRsiState();
        bBandState = s.getbBandState();
        greedyAdvice = s.getGreedyAdvice();
    }
}
