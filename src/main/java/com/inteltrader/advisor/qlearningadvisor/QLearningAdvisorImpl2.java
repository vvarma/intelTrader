package com.inteltrader.advisor.qlearningadvisor;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.Advisor;
import com.inteltrader.advisor.tawrapper.InstrumentWrapper;
import com.inteltrader.advisor.tawrapper.InstrumentWrapperImpl;
import com.inteltrader.advisor.tawrapper.TAWrapper;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/29/13
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class QLearningAdvisorImpl2 implements Advisor {
    private InstrumentWrapper instrumentWrapper;
    private Set<State> stateSet;
    private State presentState;
    private Advice presentAdvice;
    private Logger appLogger = Logger.getLogger("AppLogging");
    @Override
    public Advice getAdvice() {

        return presentAdvice;
    }

    @Override
    public Advice updatePriceAndGetAdvice(Price price) throws IOException{
        presentState=instrumentWrapper.updateWrapperAndGetStateBuilder(price,presentState,presentAdvice).build();
        presentAdvice=presentState.getGreedyAdvice();
        return presentAdvice;
    }

    public QLearningAdvisorImpl2(Instrument instrument,String... token) throws IOException {
        appLogger.info("info");
        appLogger.trace("trace");
        appLogger.warn("warn");
        appLogger.debug("debug");
        stateSet=new HashSet<State>();
        instrumentWrapper =TAWrapper.WrapMaker(instrument,token);
        presentState=instrumentWrapper.getStateBuilder().build();
        presentAdvice=Advice.HOLD;
        train(instrument,token);
    }

    private void train(Instrument instrument, String[] token) throws IOException{
        int MIN_REQ=40;
        int MAX=instrument.getPriceList().size()-1;
        Instrument testInstrument=new Instrument(instrument.getSymbolName());
        testInstrument.setPriceList(new ArrayList<Price>(instrument.getPriceList().subList(0, MIN_REQ)));
        Holdings holdings=new Holdings(testInstrument.getCurrentPrice().getClosePrice());
        Advice presentAdvice=Advice.HOLD;
        for (int iter=0;iter<100;iter++){
            InstrumentWrapper testWrapper=TAWrapper.WrapMaker(testInstrument,token);
            State presentState=testWrapper.getStateBuilder().build();
            for (Price price:instrument.getPriceList().subList(MIN_REQ,MAX)){
                if(!stateSet.contains(presentState))
                    stateSet.add(presentState);
                Iterator iterator=stateSet.iterator();
                while (iterator.hasNext()){
                    State state=(State)iterator.next();
                    if (presentState.equals(state)){
                        presentState=state;
                    }
                }
               //System.out.println("1"+presentState);
                presentState.updateReward(presentAdvice,calcReward(holdings.getQuantity(),holdings.getCurrentPrice(),price.getClosePrice()));
                Holdings.HoldingState hState=holdings.getHoldingsAndUpdateCurrentPrice(price.getClosePrice());
                presentState=testWrapper.updateWrapperAndGetStateBuilder(price,hState).build();
              // System.out.println("3"+presentState);
                presentAdvice=presentState.getNonGreedyAdvice(iter);
                holdings.updateHoldings(presentAdvice);


            }
        }
    }
    private double calcReward(int quantity, double lastPrice, double currentPrice) {
        return quantity * (currentPrice - lastPrice);
    }
    public Set<State> getStateSet() {
        return stateSet;
    }
}
