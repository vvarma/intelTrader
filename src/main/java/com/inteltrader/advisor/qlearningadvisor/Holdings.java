package com.inteltrader.advisor.qlearningadvisor;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/14/13
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Holdings {
    public enum HoldingState{
        LONG_PROFIT,LONG_LOSS,SHORT_PROFIT,SHORT_LOSS,NO_HOLDING;
    }
    public HoldingState getHoldings(int quantity,double lastPrice,double currentPrice ){
        if(quantity==0){
            return HoldingState.NO_HOLDING;
        }
        if (currentPrice>lastPrice){
            if (quantity>0){
                 return HoldingState.LONG_PROFIT;
            }   else {
                return HoldingState.SHORT_LOSS;
            }
        }else{
            if (quantity>0){
                return HoldingState.LONG_LOSS;
            }   else {
                return HoldingState.SHORT_PROFIT;
            }

        }
    }
}
