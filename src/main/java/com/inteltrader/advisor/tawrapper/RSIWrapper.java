package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.qlearningadvisor.Holdings;
import com.inteltrader.advisor.qlearningadvisor.State;
import com.inteltrader.entity.Price;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 6/25/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class RSIWrapper extends TAWrapper {
    private List<Double> rsiList;
    CalculatorRSI calculator;

    public RSIWrapper(InstrumentWrapper instrumentWrapper, String desc) throws IOException {
        super(instrumentWrapper, desc);
        rsiList = new ArrayList<Double>();
        calculator = new CalculatorRSI(Integer.MAX_VALUE);
        calculator.calcRSI(getInstrument(), rsiList);
        zeroBitwise();

    }

    private void zeroBitwise() {
        int indexMacd = rsiList.size() - 1;
        for (int i = 0; i <= indexMacd; i++) {
            if (rsiList.get(i).equals(0.0)) {
                rsiList.remove(i);
                rsiList.add(0, 0.0);
            }
        }

    }
    private void updateRsiWrapper() throws IOException {
        List<Double> tempRsiList = new ArrayList<Double>();
        calculator.calcRSI(getInstrument(), tempRsiList);
        rsiList.remove(0);
       rsiList.add(tempRsiList.get(0));
    }

    @Override
    public State.Builder updateWrapperAndGetStateBuilder(Price price, State presentState, Advice presentAdvice) throws IOException {
        return this.getWrapper().updateWrapperAndGetStateBuilder(price, presentState, presentAdvice).rsi(updateWrapperAndReturnState());
    }

    @Override
    public State.Builder updateWrapperAndGetStateBuilder(Price price, Holdings.HoldingState holdingState) throws IOException {
        return this.getWrapper().updateWrapperAndGetStateBuilder(price, holdingState).rsi(updateWrapperAndReturnState());
    }

    private CalculatorRSI.RSIState updateWrapperAndReturnState() throws IOException{
        updateRsiWrapper();
        int index = rsiList.size() - 1;
        CalculatorRSI.RSIState rsiState = calculator.getRSIState(rsiList.get(index));
        return rsiState;
    }

    @Override
    public State.Builder getStateBuilder(Holdings.HoldingState hState) throws IOException {
        int index = rsiList.size() - 1;
        CalculatorRSI.RSIState rsiState = calculator.getRSIState(rsiList.get(index));

        return this.getWrapper().getStateBuilder(hState).rsi(rsiState);
    }

    @Override
    public void updateHoldings(Advice advice) {
        this.getWrapper().updateHoldings(advice);
    }

    public List<Double> getRsiList() {
        return rsiList;
    }


}
