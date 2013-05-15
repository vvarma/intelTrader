package com.inteltrader.advisor.qlearningadvisor;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.Advisor;
import com.inteltrader.advisor.InstrumentVo;
import com.inteltrader.advisor.tawrapper.CalculatorMACD;
import com.inteltrader.advisor.tawrapper.CalculatorRSI;
import com.inteltrader.entity.Instrument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/14/13
 * Time: 2:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class QLearningAdvisor implements Advisor {
    private Set<State> states = new HashSet<State>();
    private State presentState;
    private Advice presentAdvice;
    private Holdings holdings;
    private CalculatorMACD calculatorMACD;
    private CalculatorRSI calculatorRSI;
    List<Double> macdList = new ArrayList<Double>();
    List<Double> macdSignalList = new ArrayList<Double>();
    List<Double> macdHistList = new ArrayList<Double>();
    List<Double> rsiList = new ArrayList<Double>();
    private InstrumentVo instrumentVo;
    int quantity = 0;
    double price = 0;

    @Override
    public Advice getAdvice() {
        calculatorMACD.calcMACD(instrumentVo, macdList, macdSignalList, macdHistList);
        calculatorRSI.calcRSI(instrumentVo, rsiList);
        train(instrumentVo);
        System.out.println("No of States = " + states.size());
        for (State s:states){
            System.out.println(s.toString());
        }
        int i=macdHistList.size()-1;
        presentState.updateReward(presentAdvice, calcReward(quantity,price, instrumentVo.getPriceList().get(i).getClosePrice()));
        //need a builder here
        price=instrumentVo.getPriceList().get(i).getClosePrice();
        presentState = new State(holdings.getHoldings(quantity, price, instrumentVo.getPriceList().get(i).getClosePrice()),
                calculatorMACD.getMACDState(macdHistList.get(i)), calculatorRSI.getRSIState(rsiList.get(i)));
        if (!states.contains(presentState)){
            states.add(presentState);
        }else{
           //presentState= states.get(states.indexOf(presentState));
           // presentState=states.
        }
        presentAdvice=presentState.getNonGreedyAdvice();
        quantity=updateQuantity(quantity,presentAdvice);
        return presentAdvice;  //To change body of implemented methods use File | Settings | File Templates.
        }

        private void updateState(Holdings holdings,CalculatorRSI.RSIState rsiState,CalculatorMACD.MACDState macdState){
            for (State s:states){


            }
        }

    public void train(InstrumentVo instrumentVo) {
        Advice presentAdvice=Advice.HOLD;
        State presentState = new State(Holdings.HoldingState.NO_HOLDING, CalculatorMACD.MACDState.START, CalculatorRSI.RSIState.START);
        int quantity = 0;
        double price = 0;
        for (int iter = 0; iter < 100; iter++) {
            //System.out.print("Iter "+iter+": ");
            System.out.print("size pric="+instrumentVo.getPriceList().size()+" m= "+macdHistList.size()+" r="+rsiList.size());
            for (int i = 0; i < macdHistList.size(); i++) {
                //System.out.println();
                //System.out.print("previous state :"+presentState.toString()+" previous advice :"+presentAdvice.toString());
                if (macdHistList.get(i) != 0 && rsiList.get(i) != 0) {
                     presentState.updateReward(presentAdvice, calcReward(quantity,price, instrumentVo.getPriceList().get(i).getClosePrice()));
                    //need a builder here
                    price=instrumentVo.getPriceList().get(i).getClosePrice();
                    presentState = new State(holdings.getHoldings(quantity, price, instrumentVo.getPriceList().get(i).getClosePrice()),
                            calculatorMACD.getMACDState(macdHistList.get(i)), calculatorRSI.getRSIState(rsiList.get(i)));
                    if (!states.contains(presentState)){
                         states.add(presentState);
                     }  else{
                        //presentState= states.get(states.indexOf(presentState));
                    }
                     presentAdvice=presentState.getNonGreedyAdvice();
                    quantity=updateQuantity(quantity,presentAdvice);
                   // System.out.print(" present state :"+presentState.toString()+" present advice :"+presentAdvice.toString()+" "+quantity+" "+price);

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


    public QLearningAdvisor(Instrument instrument) throws IOException {
        System.out.println("constructor called 123432");
        instrumentVo=new InstrumentVo(instrument.getSymbolName());
        instrumentVo.setPriceList(instrument.getPriceList());
        calculatorRSI = new CalculatorRSI();
        calculatorMACD = new CalculatorMACD();
        holdings = new Holdings();
        presentState = new State(Holdings.HoldingState.NO_HOLDING, CalculatorMACD.MACDState.START, CalculatorRSI.RSIState.START);
        presentAdvice = Advice.HOLD;

    }
}
