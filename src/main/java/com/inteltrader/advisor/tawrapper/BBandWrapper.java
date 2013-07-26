package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.InstrumentWrapper;
import com.inteltrader.advisor.qlearning.State;
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
    public State.Builder updateWrapperAndGetStateBuilder(Price price) throws IOException {

        return this.getWrapper().updateWrapperAndGetStateBuilder(price).bband(updateBBandAndGetState());  //To change body of implemented methods use File | Settings | File Templates.
    }

    private CalculatorBollingerBands.BBandState updateBBandAndGetState() {
        updateBband();
        int index=getInstrument().getPriceList().size()-1;
        return calculator.getBBandState(upperBand.get(index),middleBand.get(index),lowerBand.get(index),getInstrument().getCurrentPrice().getClosePrice());
    }

    private void updateBband() {
        List<Double> tempUpperBand = new ArrayList<Double>();
        List<Double> tempMiddleBand = new ArrayList<Double>();
        List<Double> tempLowerBand = new ArrayList<Double>();
        calculator.calcBollBands(getInstrument(),1,tempUpperBand,tempMiddleBand,tempLowerBand);
        upperBand.remove(0);
        middleBand.remove(0);
        lowerBand.remove(0);
        upperBand.add(tempUpperBand.get(0));
        middleBand.add(tempMiddleBand.get(0));
        lowerBand.add(tempLowerBand.get(0));
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
