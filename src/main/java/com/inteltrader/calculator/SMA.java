package com.inteltrader.calculator;

import com.inteltrader.entity.Price;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/23/13
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class SMA {
    /*

    Daily Closing Prices: 11,12,13,14,15,16,17

    First day of 5-day SMA: (11 + 12 + 13 + 14 + 15) / 5 = 13

    Second day of 5-day SMA: (12 + 13 + 14 + 15 + 16) / 5 = 14

    Third day of 5-day SMA: (13 + 14 + 15 + 16 + 17) / 5 = 15


    */
    public static double calculateSMA(int period, List<Price> priceList) {
        int doubleListSize = priceList.size();
        Double smaSum = 0.0;
        for (Price price : priceList.subList(doubleListSize - period, doubleListSize)) {
            smaSum += price.getClosePrice();
        }
        return smaSum / period;
    }
}
