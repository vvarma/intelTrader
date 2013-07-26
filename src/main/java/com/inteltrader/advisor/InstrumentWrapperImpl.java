package com.inteltrader.advisor;

import com.inteltrader.advisor.qlearning.Holdings;
import com.inteltrader.advisor.qlearning.State;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/27/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class InstrumentWrapperImpl implements InstrumentWrapper {
    private InstrumentAo instrumentAo;
    //changeTo
    private int MAX_SIZE = Integer.MAX_VALUE;
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
        holdings = new Holdings();


    }

    @Override
    public State.Builder getStateBuilder(int i) throws IndexOutOfBoundsException {
        if (i>=getInstrument().getPriceList().size())
            throw new IndexOutOfBoundsException();
        return new State.Builder();
    }

    private double calcReward(int quantity, double lastPrice, double currentPrice) {
        return quantity * (currentPrice - lastPrice);
    }

    public InstrumentWrapperImpl(InstrumentAo instrumentAo) {
        this.instrumentAo = instrumentAo;
    }


    public State.Builder updateWrapperAndGetStateBuilder(Price price) {
        if (instrumentAo.getPriceList().size() < MAX_SIZE) {
            instrumentAo.getPriceList().add(price);
        } else {
            instrumentAo.getPriceList().remove(0);
            instrumentAo.getPriceList().add(price);
        }
        return new State.Builder();
    }

    @Override
    public InstrumentAo getInstrument() {
        return instrumentAo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstrumentWrapperImpl)) return false;

        InstrumentWrapperImpl that = (InstrumentWrapperImpl) o;

        if (instrumentAo != null ? !instrumentAo.equals(that.instrumentAo) : that.instrumentAo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return instrumentAo != null ? instrumentAo.hashCode() : 0;
    }
}
