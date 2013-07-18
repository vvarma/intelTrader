package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.qlearningadvisor.State;
import com.inteltrader.indicators.CalculatorRSI;
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
    private String desc;
    private List<Double> rsiList;
    CalculatorRSI calculator;

    public RSIWrapper(InstrumentWrapper instrumentWrapper, String desc) throws IOException {
        super(instrumentWrapper);
        this.desc=desc;
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
        calculator.setNoOutEle(1);
        calculator.calcRSI(getInstrument(), tempRsiList);
        rsiList.remove(0);
       rsiList.add(tempRsiList.get(0));
    }

    @Override
    public State.Builder getStateBuilder(int index) throws IndexOutOfBoundsException {
        return this.getWrapper().getStateBuilder(index).rsi(getRsiState(index));
    }

    private CalculatorRSI.RSIState getRsiState(int index) {
        return calculator.getRSIState(rsiList.get(index));  //To change body of created methods use File | Settings | File Templates.
    }

    private CalculatorRSI.RSIState updateWrapperAndReturnState() throws IOException{
        updateRsiWrapper();
        int index = rsiList.size() - 1;
        CalculatorRSI.RSIState rsiState = calculator.getRSIState(rsiList.get(index));
        return rsiState;
    }

    public List<Double> getRsiList() {
        return rsiList;
    }

    @Override
    public State.Builder updateWrapperAndGetStateBuilder(Price price) throws IOException {
        return this.getWrapper().updateWrapperAndGetStateBuilder(price).rsi(updateWrapperAndReturnState());
    }

    @Override
    public String getDesc() {
        return desc;    //To change body of overridden methods use File | Settings | File Templates.
    }
}
