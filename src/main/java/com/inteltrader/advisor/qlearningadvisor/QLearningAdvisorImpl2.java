package com.inteltrader.advisor.qlearningadvisor;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.Advisor;
import com.inteltrader.advisor.tawrapper.InstrumentWrapper;
import com.inteltrader.advisor.tawrapper.TAWrapper;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;
import com.inteltrader.entity.States;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/29/13
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class QLearningAdvisorImpl2 implements Advisor {
    private InstrumentWrapper instrumentWrapper;
    private Set<State> stateSet;
    private State presentState;
    private Advice presentAdvice;
    private Logger logger = Logger.getLogger(QLearningAdvisorImpl2.class);

    public QLearningAdvisorImpl2(States states, Instrument instrument, Holdings.HoldingState hState, String... token) throws IOException {
        instrumentWrapper = TAWrapper.WrapMaker(instrument, token);
        stateSet = states.getStateSet();
        presentState = instrumentWrapper.getStateBuilder(hState).build();
        if (!stateSet.contains(presentState))
            stateSet.add(presentState);
        Iterator iterator = stateSet.iterator();
        while (iterator.hasNext()) {
            State state = (State) iterator.next();
            if (presentState.equals(state)) {
                presentState = state;
            }
        }
        presentAdvice = presentState.getGreedyAdvice();
    }

    @Override
    public Advice getAdvice() {

        return presentAdvice;
    }

    @Override
    public Advice updatePriceAndGetAdvice(Price price) throws IOException {
        presentState = instrumentWrapper.updateWrapperAndGetStateBuilder(price, presentState, presentAdvice).build();
        presentAdvice = presentState.getGreedyAdvice();
        return presentAdvice;
    }

    public QLearningAdvisorImpl2(Instrument instrument, Holdings.HoldingState hState, String... token) throws IOException {


        stateSet = new HashSet<State>();
        train(instrument, token);
        instrumentWrapper = TAWrapper.WrapMaker(instrument, token);

        presentState = instrumentWrapper.getStateBuilder(hState).build();
        if (!stateSet.contains(presentState))
            stateSet.add(presentState);
        Iterator iterator = stateSet.iterator();
        while (iterator.hasNext()) {
            State state = (State) iterator.next();
            if (presentState.equals(state)) {
                presentState = state;
            }
        }
        presentAdvice = presentState.getGreedyAdvice();

    }

    private void train(Instrument instrument, String[] token) throws IOException {
        logger.debug("Training :" + instrument.getSymbolName() + " " + token);
        int MIN_REQ = 40;
        int MAX = instrument.getPriceList().size() - 1;
        Holdings holdings = new Holdings();
        Advice presentAdvice = Advice.HOLD;
        for (int iter = 0; iter < 100000; iter++) {
            System.out.println("Iter"+iter);
            //logger.debug("Iter :" +iter);
            Instrument testInstrument = new Instrument(instrument.getSymbolName());
            testInstrument.setPriceList(new ArrayList<Price>(instrument.getPriceList().subList(0, MIN_REQ)));
            InstrumentWrapper testWrapper = TAWrapper.WrapMaker(testInstrument, token);
            //logger.debug("Size of test Instrument" +testWrapper.getInstrument().getPriceList().size());
            State presentState = testWrapper.getStateBuilder(holdings.getHoldingsAndUpdateCurrentPrice(testInstrument.getCurrentPrice().getClosePrice())).build();
            for (Price price : instrument.getPriceList().subList(MIN_REQ, MAX)) {
                if (!stateSet.contains(presentState))
                    stateSet.add(presentState);
                Iterator iterator = stateSet.iterator();
                while (iterator.hasNext()) {
                    State state = (State) iterator.next();
                    if (presentState.equals(state)) {
                        presentState = state;
                    }
                }
                presentState.updateReward(presentAdvice, calcReward(holdings.getQuantity(), holdings.getCurrentPrice(), price.getClosePrice()));
                Holdings.HoldingState hState = holdings.getHoldingsAndUpdateCurrentPrice(price.getClosePrice());
                presentState = testWrapper.updateWrapperAndGetStateBuilder(price, hState).build();
                presentAdvice = presentState.getNonGreedyAdvice();
                holdings.updateHoldings(presentAdvice);


            }

        }
        logger.debug("Training done");
        for (State s : stateSet) {
            logger.debug(s);
        }
    }

    private double calcReward(int quantity, double lastPrice, double currentPrice) {
        if (quantity == 0) {
            return currentPrice - lastPrice;
        }
        return quantity * (currentPrice - lastPrice);
    }

    public Set<State> getStateSet() {
        return stateSet;
    }

    @Override
    public States getStates() {
        States states = new States();
        states.setSymbolNamme(instrumentWrapper.getInstrument().getSymbolName());
        states.setPresentState(presentState);
        states.setStateSet(stateSet);
        states.setPresentAdvice(presentAdvice);
        return states;
    }
}
