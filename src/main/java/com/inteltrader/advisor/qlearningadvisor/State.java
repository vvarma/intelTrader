package com.inteltrader.advisor.qlearningadvisor;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.tawrapper.CalculatorMACD;
import com.inteltrader.advisor.tawrapper.CalculatorRSI;

import javax.persistence.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/14/13
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STATE_ID")
    private int id = 0;
    private CalculatorMACD.MACDState macdState;
    private CalculatorRSI.RSIState rsiState;
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyEnumerated
    @JoinTable(
            name = "ACTION_REWARD",
            joinColumns = @JoinColumn(name = "STATE_ID")
    )
    private Map<Advice, Double> actionRewardMap;
    private Random random = new Random();

    public static class Builder {
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

        public Builder() {

        }

        public State build() {
            return new State(this);
        }
    }

    private State(Builder builder) {
        macdState = builder.macdState;
        rsiState = builder.rsiState;
        actionRewardMap = new HashMap<Advice, Double>();
        actionRewardMap.put(Advice.BUY, 0.0);
        actionRewardMap.put(Advice.SELL, 0.0);
        actionRewardMap.put(Advice.HOLD, 0.0);
    }

    public void updateReward(Advice advice, double reward) {
         actionRewardMap.put(advice, reward);
        //normaliseRewards();
    }


    public void normaliseRewards() {
        List<Double> valSet = new ArrayList<Double>(actionRewardMap.values());
        double maxValue = Math.abs(valSet.get(0));
        for (double d : valSet) {
            double absD = Math.abs(d);
            if (absD > maxValue) {
                maxValue = absD;
            }
        }
        if (maxValue != 0.0)
            for (Advice a : actionRewardMap.keySet()) {
                actionRewardMap.put(a, actionRewardMap.get(a) / maxValue);
            }
    }

    public State(CalculatorMACD.MACDState macdState, CalculatorRSI.RSIState rsiState) {
        this.macdState = macdState;
        this.rsiState = rsiState;
        actionRewardMap = new HashMap<Advice, Double>();
        actionRewardMap.put(Advice.BUY, 0.0);
        actionRewardMap.put(Advice.SELL, 0.0);
        actionRewardMap.put(Advice.HOLD, 0.0);
    }

    public double getRewardForAdvice(Advice advice){
        return actionRewardMap.get(advice);
    }
    public Advice getGreedyAdvice() {
        double maxReward = -Double.MAX_VALUE;
        Advice maxAdvice = null;
        for (Advice advice : actionRewardMap.keySet()) {
            if (actionRewardMap.get(advice) > maxReward) {
                maxReward = actionRewardMap.get(advice);
                maxAdvice = advice;
            }

        }
        return maxAdvice;
    }

    public Advice getNonGreedyAdvice() {
        Advice[] arrAdvice = {Advice.BUY, Advice.HOLD, Advice.SELL};
        //int i = random.nextInt(3);
        //changeTo check
        int i = ((int)(Math.random()*137 +17))%3;
        Advice ret = arrAdvice[i];

        //System.out.println("1232321" + ret.toString() + " " + iter);
        return ret;
        // return arrAdvice[random.nextInt(3)];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        if (macdState != state.macdState) return false;
        if (rsiState != state.rsiState) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = macdState != null ? macdState.hashCode() : 0;
        result = 31 * result + (rsiState != null ? rsiState.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "State{" +
                "macdState=" + macdState +
                ", rsiState=" + rsiState +
                ", actionRewardMap=" + actionRewardMap +
                '}';
    }

    public State() {

    }
}
