package com.inteltrader.advisor.qlearningadvisor;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.Advisor;
import com.inteltrader.advisor.InstrumentVo;
import com.inteltrader.advisor.tawrapper.CalculatorMACD;
import com.inteltrader.advisor.tawrapper.CalculatorRSI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/14/13
 * Time: 2:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class QLearningAdvisor implements Advisor {
    private List<State> states = new ArrayList<State>();
    private State presentState;
    private Advice presentAdvice;
    private Holdings holdings;
    private CalculatorMACD calculatorMACD;
    private CalculatorRSI calculatorRSI;
    List<Double> macdList = new ArrayList<Double>();
    List<Double> macdSignalList = new ArrayList<Double>();
    List<Double> macdHistList = new ArrayList<Double>();
    List<Double> rsiList = new ArrayList<Double>();

    @Override
    public Advice getAdvice() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Advice getAdvice(InstrumentVo instrumentVo) {

        calculatorMACD.calcMACD(instrumentVo, macdList, macdSignalList, macdHistList);
        calculatorRSI.calcRSI(instrumentVo, rsiList);
        train(instrumentVo);
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void train(InstrumentVo instrumentVo) {
        int quantity = 0;
        double price = 0;
        for (int iter = 0; iter < 100; iter++) {
            for (int i = 0; i < macdHistList.size(); i++) {
                if (macdHistList.get(i) != 0 && rsiList.get(i) != 0) {
                    presentState.updateReward(presentAdvice, calcReward(quantity,price, instrumentVo.getPriceList().get(i).getClosePrice()));
                    //need a builder here
                    presentState = new State(holdings.getHoldings(quantity, price, instrumentVo.getPriceList().get(i).getClosePrice()),
                            calculatorMACD.getMACDState(macdHistList.get(i)), calculatorRSI.getRSIState(rsiList.get(i)));
                    if (!states.contains(presentState)){
                         states.add(presentState);
                     }
                     presentAdvice=presentState.getNonGreedyAdvice();
                    quantity=updateQuantity(quantity,presentAdvice);

                }
            }

        }

    }
    private int updateQuantity(int quantity,Advice advice){
        if(advice.equals(Advice.BUY))
            return ++quantity;
        else if (advice.equals(Advice.SELL))
            return --quantity;
        else
            return quantity;
    }
    private double calcReward(int quantity,double lastPrice,double currentPrice){
        return quantity*(currentPrice-lastPrice);
    }


    public QLearningAdvisor() throws IOException {
        calculatorRSI = new CalculatorRSI();
        calculatorMACD = new CalculatorMACD();
        holdings = new Holdings();
        presentState = new State(Holdings.HoldingState.NO_HOLDING, CalculatorMACD.MACDState.START, CalculatorRSI.RSIState.START);
        presentAdvice = Advice.HOLD;

    }
}
