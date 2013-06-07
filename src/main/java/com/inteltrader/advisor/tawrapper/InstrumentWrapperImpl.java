package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.InstrumentAo;
import com.inteltrader.advisor.qlearningadvisor.Holdings;
import com.inteltrader.advisor.qlearningadvisor.State;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/27/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class InstrumentWrapperImpl implements InstrumentWrapper {
    private InstrumentAo instrumentAo;
    private int MAX_SIZE = 2 * 365;
    private Holdings holdings;

    public InstrumentWrapperImpl(Instrument instrument) {
        instrumentAo = new InstrumentAo();
        this.instrumentAo.setSymbolName(instrument.getSymbolName());
        if (instrument.getPriceList().size() < MAX_SIZE) {
            this.instrumentAo.setPriceList(instrument.getPriceList());
        } else {
            int LIST_SIZE = instrument.getPriceList().size();
            this.instrumentAo.setPriceList(instrument.getPriceList().subList(LIST_SIZE - MAX_SIZE, LIST_SIZE));
        }
        holdings = new Holdings(instrumentAo.getCurrentPrice().getClosePrice());


    }

    @Override
    public void updateHoldings(Advice advice) {
        switch (advice) {
            case BUY:
                holdings.addQuantity(1);
                break;
            case SELL:
                holdings.addQuantity(-1);
                break;
            case HOLD:
                break;
        }
    }

    @Override
    public State.Builder getStateBuilder() throws IOException {
        return new State.Builder(Holdings.HoldingState.NO_HOLDING);
    }

    @Override
    public State.Builder updateWrapperAndGetStateBuilder(Price price, State presentState, Advice presentAdvice) {
        double reward = calcReward(holdings.getQuantity(), holdings.getCurrentPrice(), price.getClosePrice());
        presentState.updateReward(presentAdvice, reward);
        System.out.println(holdings);
        State.Builder stateBuilderToReturn = new State.Builder(holdings.getHoldingsAndUpdateCurrentPrice(price.getClosePrice()));
        updateWrapper(price);
        return stateBuilderToReturn;
    }

    private double calcReward(int quantity, double lastPrice, double currentPrice) {
        return quantity * (currentPrice - lastPrice);
    }

    public InstrumentWrapperImpl(InstrumentAo instrumentAo) {
        this.instrumentAo = instrumentAo;
    }


    private void updateWrapper(Price price) {
        if (instrumentAo.getPriceList().size() < MAX_SIZE) {
            instrumentAo.getPriceList().add(price);
        } else {
            instrumentAo.getPriceList().remove(0);
            instrumentAo.getPriceList().add(price);
        }
    }

    @Override
    public InstrumentAo getInstrument() {
        return instrumentAo;
    }
}
