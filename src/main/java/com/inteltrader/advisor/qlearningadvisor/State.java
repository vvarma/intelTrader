package com.inteltrader.advisor.qlearningadvisor;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.tawrapper.CalculatorMACD;
import com.inteltrader.advisor.tawrapper.CalculatorRSI;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/14/13
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class State implements States {
    private final Holdings.HoldingState holdingState;
    private CalculatorMACD.MACDState macdState;
    private CalculatorRSI.RSIState rsiState;
    private Map<Advice, Double> actionRewardMap;
    private Random random=new Random();

    public static class Builder {
        private final Holdings.HoldingState holdingState;
        private CalculatorMACD.MACDState macdState = null;
        private CalculatorRSI.RSIState rsiState = null;

        public Builder macd(CalculatorMACD.MACDState macdState) {
            this.macdState = macdState;
            return this;
        }

        public Builder rsi(CalculatorRSI.RSIState rsiState) {
            this.rsiState = rsiState;
            return this;
        }

        public Builder(Holdings.HoldingState holdingState) {
            this.holdingState = holdingState;
        }

        public State build() {
            return new State(this);
        }
    }

    private State(Builder builder) {
        holdingState = builder.holdingState;
        macdState = builder.macdState;
        rsiState = builder.rsiState;
        actionRewardMap = new HashMap<Advice, Double>();
        actionRewardMap.put(Advice.BUY, 0.0);
        actionRewardMap.put(Advice.SELL, 0.0);
        actionRewardMap.put(Advice.HOLD, 0.0);
    }

    public void updateReward(Advice advice, double reward) {
        double updateReward = reward + actionRewardMap.get(advice);
        actionRewardMap.put(advice, updateReward);
    }

    public State(Holdings.HoldingState holdingState, CalculatorMACD.MACDState macdState, CalculatorRSI.RSIState rsiState) {
        this.holdingState = holdingState;
        this.macdState = macdState;
        this.rsiState = rsiState;
        actionRewardMap = new HashMap<Advice, Double>();
        actionRewardMap.put(Advice.BUY, 0.0);
        actionRewardMap.put(Advice.SELL, 0.0);
        actionRewardMap.put(Advice.HOLD, 0.0);
    }

    public Advice getGreedyAdvice() {
        double maxReward = Double.MIN_VALUE;
        Advice maxAdvice = null;
        for (Advice advice : actionRewardMap.keySet()) {
            if (actionRewardMap.get(advice) > maxReward) {
                maxAdvice = advice;
            }
        }
        return maxAdvice;
    }

    public Advice getNonGreedyAdvice(int iter) {
        Advice[] arrAdvice = {Advice.BUY, Advice.HOLD, Advice.SELL};
        int i = random.nextInt(3);
        Advice ret = arrAdvice[i];

        //System.out.println("1232321" + ret.toString() + " " + iter);
        return ret;
        // return arrAdvice[random.nextInt(3)];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (holdingState != state.holdingState) return false;
        if (macdState != state.macdState) return false;
        if (rsiState != state.rsiState) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = holdingState.hashCode();
        result = 31 * result + (macdState != null ? macdState.hashCode() : 0);
        result = 31 * result + (rsiState != null ? rsiState.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "State{" +
                "holdingState=" + holdingState +
                ", macdState=" + macdState +
                ", rsiState=" + rsiState +
                ", actionRewardMap=" + actionRewardMap +
                '}';
    }
}
