package com.inteltrader.advisor.qlearningadvisor;

import com.inteltrader.advisor.Advice;

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
    private int quantity;
    private double currentPrice;

    public Holdings( double currentPrice) {
        this.quantity = 0;
        this.currentPrice = currentPrice;
    }
    public void addQuantity(int quantity){
        this.quantity+=quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void updateHoldings(Advice advice) {
        switch (advice) {
            case BUY:
                int buy=1;
                int presentQusnt=this.getQuantity();
                if (presentQusnt<0)
                    buy=buy-presentQusnt;
                this.addQuantity(buy);
                break;
            case SELL:
                int sell=-1;
                int presentQuant=this.getQuantity();
                if (presentQuant>0)
                    sell=sell-presentQuant;
                this.addQuantity(sell);
                break;
            case HOLD:
                break;
        }
    }
    public HoldingState getHoldingsAndUpdateCurrentPrice(double currentPrice){
        HoldingState toBeReturned=null;
        if(quantity==0){
            toBeReturned= HoldingState.NO_HOLDING;
        }
        if (this.currentPrice<currentPrice){
            if (quantity>0){
                toBeReturned= HoldingState.LONG_PROFIT;
            }   else {
                toBeReturned= HoldingState.SHORT_LOSS;
            }
        }else{
            if (quantity>0){
                toBeReturned= HoldingState.LONG_LOSS;
            }   else {
                toBeReturned= HoldingState.SHORT_PROFIT;
            }

        }
        this.currentPrice=currentPrice;
        return toBeReturned;

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

    @Override
    public String toString() {
        return "Holdings{" +
                "quantity=" + quantity +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
