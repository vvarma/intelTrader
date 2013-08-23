package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.InstrumentWrapper;
import com.inteltrader.advisor.qlearning.State;
import com.inteltrader.entity.Price;
import com.inteltrader.indicators.CalculatorStochastic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 8/5/13
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class StochasticWrapper extends TAWrapper {
    private String desc;
    private CalculatorStochastic calculator;
    private List<Double> kResults;
    private List<Double> dResults;

    public StochasticWrapper(InstrumentWrapper instrumentWrapper, String desc) {
        super(instrumentWrapper);
        this.desc = desc;
        calculator = new CalculatorStochastic();
        kResults = new ArrayList<Double>();
        dResults = new ArrayList<Double>();
        calculator.calcStochastic(getInstrument(), kResults, dResults);
        zeroBitwise();
    }

    private void zeroBitwise() {
        int indexMacd = kResults.size() - 1;
        for (int i = 0; i <= indexMacd; i++) {
            if (kResults.get(i).equals(0.0)) {
                kResults.remove(i);
                dResults.remove(i);
                kResults.add(0, 0.0);
                dResults.add(0, 0.0);
            }
        }

    }

    @Override
    public State.Builder getStateBuilder(int i) throws IndexOutOfBoundsException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public State.Builder updateWrapperAndGetStateBuilder(Price price) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
