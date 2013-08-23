package com.inteltrader.entity;

import javax.persistence.Embeddable;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/2/13
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class Price implements Cloneable {

    private long priceId;
    private Calendar timeStamp;
    private Double closePrice, openPrice, lowPrice, highPrice, lastClosePrice;
    private long totalTradedQuantity;


    public Price(Calendar timeStamp, Double closePrice, Double openPrice, Double lowPrice, Double highPrice, Double lastClosePrice, long totalTradedQuantity) {
        this.timeStamp = timeStamp;
        this.closePrice = closePrice;
        this.openPrice = openPrice;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        this.lastClosePrice = lastClosePrice;
        this.totalTradedQuantity = totalTradedQuantity;
    }

    public long getTotalTradedQuantity() {
        return totalTradedQuantity;
    }

    public void setTotalTradedQuantity(long totalTradedQuantity) {
        this.totalTradedQuantity = totalTradedQuantity;
    }

    public long getPriceId() {
        return priceId;
    }

    public void setPriceId(long priceId) {
        this.priceId = priceId;
    }

    public Calendar getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Calendar timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Double getLastClosePrice() {
        return lastClosePrice;
    }

    public void setLastClosePrice(Double lastClosePrice) {
        this.lastClosePrice = lastClosePrice;
    }

    public Price() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return "Price{" +
                "priceId=" + priceId +
                ", timeStamp=" + timeStamp.getTime() +
                ", closePrice=" + closePrice +
                ", openPrice=" + openPrice +
                ", lowPrice=" + lowPrice +
                ", highPrice=" + highPrice +
                ", lastClosePrice=" + lastClosePrice +
                ", totalTradedQuantity=" + totalTradedQuantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;

        Price price = (Price) o;

        if (priceId != price.priceId) return false;
        if (!timeStamp.equals(price.timeStamp)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (priceId ^ (priceId >>> 32));
        result = 31 * result + timeStamp.hashCode();
        return result;
    }
}
