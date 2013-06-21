package com.inteltrader.advisor.simpleadvisor;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.Advisor;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;
import com.inteltrader.entity.States;
import com.inteltrader.util.InvalidAdviceException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 1/5/13
 * Time: 6:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleAdvisor implements Advisor {
    private Map<String,Strategy> strategyMap=new HashMap<String, Strategy>();
    @Override
    public Advice getAdvice() {
        Advice advice=Advice.HOLD;
        for (Strategy strategy:strategyMap.values()){
            advice=collateAdvice(advice,strategy.getStrategicAdvice());
        }
        return advice;
    }

    @Override
    public Advice updatePriceAndGetAdvice(Price price) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public States getStates() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private Advice collateAdvice(Advice advice1, Advice advice2){
        switch (advice1){
            case BUY:
                switch (advice2){
                    case BUY:
                        return Advice.BUY;
                    case SELL:
                        return Advice.HOLD;
                    case HOLD:
                        return Advice.BUY;
                    default:
                        throw new InvalidAdviceException();
                }
            case HOLD:
                switch (advice2){
                    case BUY:
                        return Advice.BUY;
                    case SELL:
                        return Advice.SELL;
                    case HOLD:
                        return Advice.HOLD;
                    default:
                        throw new InvalidAdviceException();
                }
            case SELL:
                switch (advice2){
                    case BUY:
                        return Advice.HOLD;
                    case SELL:
                        return Advice.SELL;
                    case HOLD:
                        return Advice.SELL;
                    default:
                        throw new InvalidAdviceException();
                }
            default:
                throw new InvalidAdviceException();
        }
    }

   public void addStrategy(String strategyName, Strategy strategy){
       strategyMap.put(strategyName,strategy);
   }

    public static Advisor buildAdvisor(Instrument instrument,String... s) throws IOException {
        SimpleAdvisor advisor=new SimpleAdvisor();
        List<String> paramList=Arrays.asList(s);
        if (paramList.contains("MACD")){
            advisor.addStrategy("MACD",new StrategyMACD(instrument));
        }
        if (paramList.contains("RSI")){
            advisor.addStrategy("RSI",new StrategyRSI(instrument));
        }
        return advisor;
    }
}
