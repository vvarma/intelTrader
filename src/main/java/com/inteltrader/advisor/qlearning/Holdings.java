package com.inteltrader.advisor.qlearning;

import com.inteltrader.advisor.Advice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/14/13
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Holdings {
    public enum HoldingState {
        LONG_PROFIT, LONG_LOSS, SHORT_PROFIT, SHORT_LOSS, NO_HOLDING;
    }

    private int quantity;
    private double currentPrice;
    private List<Transactions> transactionsList = new ArrayList<Transactions>();

    public Holdings() {
        this.quantity = 0;
        this.currentPrice = 0;
    }

    public double calcPnl() {
        double pnl = 0;
        double invested = 0;
        for (Transactions t : transactionsList) {
            invested += (t.getQuantity() * t.getTransactionPrice());
        }
        pnl = (quantity * currentPrice) - invested;
        return pnl;
    }

    public void addQuantity(int quantity) {
        transactionsList.add(new Transactions(quantity, currentPrice));
        this.quantity += quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void updateHoldings(Advice advice) {
        switch (advice) {
            case BUY:
                int buy = 1;
                int presentQusnt = this.getQuantity();
                if (presentQusnt < 0)
                    buy = -presentQusnt;
                this.addQuantity(buy);
                break;
            case SELL:
                int sell = -1;
                int presentQuant = this.getQuantity();
                if (presentQuant > 0)
                    sell = -presentQuant;
                this.addQuantity(sell);
                break;
            case HOLD:
                break;
        }
    }

    public HoldingState getHoldingsAndUpdateCurrentPrice(double currentPrice) {
        HoldingState toBeReturned = null;
        if (quantity == 0) {
            this.currentPrice = currentPrice;
            return HoldingState.NO_HOLDING;
        }
        if (this.currentPrice < currentPrice) {
            if (quantity > 0) {
                toBeReturned = HoldingState.LONG_PROFIT;
            } else {
                toBeReturned = HoldingState.SHORT_LOSS;
            }
        } else {
            if (quantity > 0) {
                toBeReturned = HoldingState.LONG_LOSS;
            } else {
                toBeReturned = HoldingState.SHORT_PROFIT;
            }

        }
        this.currentPrice = currentPrice;
        return toBeReturned;

    }

    public HoldingState getHoldings(int quantity, double lastPrice, double currentPrice) {
        if (quantity == 0) {
            return HoldingState.NO_HOLDING;
        }
        if (currentPrice > lastPrice) {
            if (quantity > 0) {
                return HoldingState.LONG_PROFIT;
            } else {
                return HoldingState.SHORT_LOSS;
            }
        } else {
            if (quantity > 0) {
                return HoldingState.LONG_LOSS;
            } else {
                return HoldingState.SHORT_PROFIT;
            }

        }
    }

    @Override
    public String toString() {
        return "Holdings{" +
                "quantity=" + quantity +
                ", currentPrice=" + currentPrice +
                ", transactionsList=" + transactionsList +
                '}';
    }
}
