package com.inteltrader.advisor;

import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 6/4/13
 * Time: 6:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class StrategyMACD extends Strategy {
    private List<Double> macdList = new ArrayList<Double>();
    private List<Double> smallPeriodMean = new ArrayList<Double>();
    private List<Double> emaMACDList = new ArrayList<Double>();
    private List<Double> largePeriodMean = new ArrayList<Double>();
    private int smallPeriod = 12, bigPeriod = 26, macdPeriod = 9;

    public StrategyMACD(Instrument instrument) {
        super(instrument);
    }

    @Override
    public Advice getStrategicAdvice() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
    public List<Double> getEmaMACDList() {
        return emaMACDList;
    }

    private Advice generateMACDSignal() {
        updateMACDList();
        updateEMACDList();
        if (macdList.get(macdList.size() - 1) > emaMACDList
                .get(macdList.size() - 1))
            return Advice.BUY;
        else if (macdList.get(macdList.size() - 1) == emaMACDList.get(macdList
                .size() - 1))
            return Advice.HOLD;
        else
            return Advice.SELL;
    }

    public List<Double> getMacdList() {
        return macdList;
    }

    private void updateEMACDList() {
        int index = getInstrumentVo().getPriceList().size();
        double multiplier = 2.0 / (macdPeriod + 1);
        if (index < macdPeriod + bigPeriod) {
            emaMACDList.add(0.0);
        } else if (index == macdPeriod + bigPeriod) {
            // emaMACDList.add(calcEMA(macdList, macdPeriod));
            double sum = 0;
            for (Double d : macdList.subList(index - macdPeriod, index))
                sum += d;
            emaMACDList.add(sum / macdPeriod);
        } else {
            emaMACDList.add((macdList.get(index - 1) - emaMACDList
                    .get(index - 2)) * multiplier + emaMACDList.get(index - 2));
        }

    }

    private void updateMACDList() {
        int index = getInstrumentVo().getPriceList().size();
        if (index < bigPeriod) {
            // calculateMean();
            smallPeriodMean.add(0.0);
            largePeriodMean.add(0.0);
            macdList.add(0.0);

        } else if (index == bigPeriod) {
            smallPeriodMean.add(calculateMean(getInstrumentVo().getPriceList().subList(index
                    - smallPeriod, index)));
            largePeriodMean.add(calculateMean(getInstrumentVo().getPriceList().subList(index
                    - bigPeriod, index)));
            macdList.add(calculateEMACD());
        } else {
            smallPeriodMean.add(calcEMA(smallPeriodMean, smallPeriod));
            largePeriodMean.add(calcEMA(largePeriodMean, bigPeriod));
            macdList.add(calculateEMACD());
            // macdList.add(calculateMACD());
        }
    }

    public Double calculateEMACD() {
        int index = getInstrumentVo().getPriceList().size() - 1;

        return smallPeriodMean.get(index) - largePeriodMean.get(index);
        // return (getInstrumentVo().getPriceList().get(index).getPrice()-macdList.get(index-1));
    }

    public Double calcEMA(List<Double> periodMean, int period) {
        double multiplier = 2.0 / (period + 1);
        int index = getInstrumentVo().getPriceList().size() - 1;

        return (getInstrumentVo().getPriceList().get(index).getClosePrice() - periodMean
                .get(index - 1)) * multiplier + periodMean.get(index - 1);

    }

    public Double calculateMean() {
        Double sum = 0.0;
        Double tempMean;
        int length = 0;
        for (Price price : getInstrumentVo().getPriceList()) {
            sum += price.getClosePrice();
            length++;
        }
        tempMean = sum / length;
        return tempMean;
    }

    public Double calculateMean(List<Price> priceList) {
        Double sum = 0.0;
        Double tempMean;
        int length = 0;
        for (Price price : priceList) {
            sum += price.getClosePrice();
            length++;
        }
        tempMean = sum / length;
        return tempMean;
    }

    @Override
    public String toString() {
        return "MACDInstrument [macdList=" + macdList + "]";
    }

}

