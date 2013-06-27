package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.qlearningadvisor.Holdings;
import com.inteltrader.advisor.qlearningadvisor.State;
import com.inteltrader.entity.Price;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/27/13
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class MACDWrapper extends TAWrapper {
    private List<Double> macdList;
    private List<Double> macdSignalList;
    private List<Double> macdHistList;
    private CalculatorMACD calculator;

    public MACDWrapper(InstrumentWrapper instrumentWrapper, String desc) throws IOException {
        super(instrumentWrapper, desc);
        macdHistList = new ArrayList<Double>();
        macdList = new ArrayList<Double>();
        macdSignalList = new ArrayList<Double>();
        calculator = new CalculatorMACD(Integer.MAX_VALUE, 0.20);
        //calculator=new CalculatorMACD();
        calculator.calcMACD(getInstrument(), macdList, macdSignalList, macdHistList);
        zeroBitwise();    //not a permanent solution
        //logger.debug("Macd Result is :" + macdHistList);
    }

    private void zeroBitwise() {
        int indexMacd = macdList.size() - 1;
        int indexMacdHist = macdHistList.size() - 1;
        int indexMacdSignal = macdSignalList.size() - 1;
        if (indexMacd == indexMacdHist && indexMacd == indexMacdSignal) {
            for (int i = 0; i <= indexMacd; i++) {
                if (macdList.get(i).equals(0.0) & macdList.get(i).equals(macdHistList.get(i)) && macdList.get(i).equals(macdSignalList.get(i))) {
                    macdList.remove(i);
                    macdHistList.remove(i);
                    macdSignalList.remove(i);
                    macdList.add(0, 0.0);
                    macdSignalList.add(0, 0.0);
                    macdHistList.add(0, 0.0);
                }
            }
        } else {
            System.err.println("Something wrong sizes should be same #123");
        }
    }

    private void updateMacdWrapper() throws IOException {
        List<Double> tempMacdList = new ArrayList<Double>();
        List<Double> tempMacdHistList = new ArrayList<Double>();
        List<Double> tempMacdSignalList = new ArrayList<Double>();
        calculator.calcMACD(getInstrument(), tempMacdList, tempMacdSignalList, tempMacdHistList);
        macdList.remove(0);
        macdHistList.remove(0);
        macdSignalList.remove(0);
        macdList.add(tempMacdList.get(0));
        macdHistList.add(tempMacdHistList.get(0));
        macdSignalList.add(tempMacdSignalList.get(0));


    }

    @Override
    public void updateHoldings(Advice advice) {
        this.getWrapper().updateHoldings(advice);
    }

    @Override
    public State.Builder updateWrapperAndGetStateBuilder(Price price, Holdings.HoldingState holdingState) throws IOException {


        return this.getWrapper().updateWrapperAndGetStateBuilder(price, holdingState).macd(updateWrapperAndReturnState());
    }

    @Override
    public State.Builder getStateBuilder(Holdings.HoldingState hState) throws IOException {
        int index = macdHistList.size() - 1;
        CalculatorMACD.MACDState macdState = calculator.getMACDState(macdHistList.get(index));

        return this.getWrapper().getStateBuilder(hState).macd(macdState);
    }

    @Override
    public State.Builder updateWrapperAndGetStateBuilder(Price price, State presentState, Advice presentAdvice) throws IOException {
        return this.getWrapper().updateWrapperAndGetStateBuilder(price, presentState, presentAdvice).macd(updateWrapperAndReturnState());
    }

    private CalculatorMACD.MACDState updateWrapperAndReturnState() throws IOException {
        updateMacdWrapper();
        int index = macdHistList.size() - 1;
        CalculatorMACD.MACDState macdState = calculator.getMACDState(macdHistList.get(index));
        return macdState;

    }

    public List<Double> getMacdList() {
        return macdList;
    }

    public List<Double> getMacdSignalList() {
        return macdSignalList;
    }

    public List<Double> getMacdHistList() {
        return macdHistList;
    }

}
