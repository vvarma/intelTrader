package com.inteltrader.advisor;

import com.inteltrader.entity.Instrument;
import com.inteltrader.util.InvalidAdviceException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 1/5/13
 * Time: 6:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleAdvisor {
    private Map<String,Strategy> strategyMap;
    @Override
    public Advice getAdvice() {
        Advice advice=Advice.HOLD;
        for (Strategy strategy:strategyMap.values()){
            advice=collateAdvice(advice,strategy.getStrategicAdvice());
        }
        return advice;
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

    public static Advisor buildAdvisor(Instrument instrument,String... s){
        SimpleAdvisor advisor=new SimpleAdvisor();
        List<String> paramList=Arrays.asList(s);
        if (paramList.contains("MACD")){
            advisor.addStrategy("MACD",new StrategyMACD(instrument));
        }
        return advisor;
    }
}
