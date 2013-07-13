package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.qlearningadvisor.Holdings;
import com.inteltrader.advisor.qlearningadvisor.State;
import com.inteltrader.indicators.CalculatorBollingerBands;
import com.inteltrader.entity.Price;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 7/4/13
 * Time: 12:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BBandWrapper extends TAWrapper {
    private String desc;
    private CalculatorBollingerBands calculator;
    private List<Double> upperBand;
    private List<Double> middleBand;
    private List<Double> lowerBand;
    public BBandWrapper(InstrumentWrapper instrumentWrapper, String desc) {
        super(instrumentWrapper);
        this.desc=desc;
        calculator=new CalculatorBollingerBands();
        upperBand=new ArrayList<Double>();
        middleBand=new ArrayList<Double>();
        lowerBand=new ArrayList<Double>();
        System.out.println("onex");
        calculator.calcBollBands(getInstrument(),Integer.MAX_VALUE,upperBand,middleBand,lowerBand);
        zeroBitwise();
    }
    private void zeroBitwise() {
        int indexMacd = upperBand.size() - 1;
        for (int i = 0; i <= indexMacd; i++) {
            if (upperBand.get(i).equals(0.0)&&middleBand.get(i).equals(0.0)&&lowerBand.get(i).equals(0.0)) {
                upperBand.remove(i);
                middleBand.remove(i);
                lowerBand.remove(i);
                upperBand.add(0, 0.0);
                middleBand.add(0, 0.0);
                lowerBand.add(0, 0.0);
            }
        }

    }

    @Override
    public State.Builder getStateBuilder(int i) throws IndexOutOfBoundsException {
        return this.getWrapper().getStateBuilder(i).bband(calculator.getBBandState(upperBand.get(i),middleBand.get(i),lowerBand.get(i),getInstrument().getPriceList().get(i).getClosePrice()));  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public State.Builder updateWrapperAndGetStateBuilder(Price price, State presentState, Advice presentAdvice) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public State.Builder updateWrapperAndGetStateBuilder(Price price, Holdings.HoldingState holdingState) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public State.Builder getStateBuilder(Holdings.HoldingState hState) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateHoldings(Advice advice) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getDesc() {
        return desc;    //To change body of overridden methods use File | Settings | File Templates.
    }

    public List<Double> getUpperBand() {
        return upperBand;
    }

    public List<Double> getMiddleBand() {
        return middleBand;
    }

    public List<Double> getLowerBand() {
        return lowerBand;
    }
}
