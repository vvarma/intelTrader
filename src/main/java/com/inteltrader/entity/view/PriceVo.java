package com.inteltrader.entity.view;

import com.inteltrader.entity.Price;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/22/13
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class PriceVo {
    private Date timeStamp;
    private Double closePrice,openPrice,lowPrice,highPrice,lastClosePrice;
    private long totalTradedQuantity;

    public PriceVo(Price price) {
        closePrice=price.getClosePrice();
        openPrice=price.getOpenPrice();
        lowPrice=price.getLowPrice();
        highPrice=price.getHighPrice();
        lastClosePrice=price.getLastClosePrice();
        totalTradedQuantity=price.getTotalTradedQuantity();
        timeStamp=price.getTimeStamp().getTime();
    }

    public PriceVo(Date timeStamp, Double closePrice, Double openPrice, Double lowPrice, Double highPrice, Double lastClosePrice, long totalTradedQuantity) {
        this.timeStamp = timeStamp;
        this.closePrice = closePrice;
        this.openPrice = openPrice;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        this.lastClosePrice = lastClosePrice;
        this.totalTradedQuantity = totalTradedQuantity;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public Double getLastClosePrice() {
        return lastClosePrice;
    }

    public long getTotalTradedQuantity() {
        return totalTradedQuantity;
    }
}
