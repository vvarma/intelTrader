package com.inteltrader.indicators;

import com.inteltrader.advisor.InstrumentAo;
import com.inteltrader.entity.Instrument;
import com.inteltrader.util.Global;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/14/13
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorMACD {

    private Core core = new Core();
    int fastPeriod;
    int slowPeriod;
    int signalPeriod;
    int noOutEle;
    double threshold;
    double result;

    public CalculatorMACD(int noOutEle, double threshold) {
        fastPeriod = Integer.MIN_VALUE;
        slowPeriod = Integer.MIN_VALUE;
        signalPeriod = Integer.MIN_VALUE;
        this.noOutEle = noOutEle;
        this.threshold = threshold;
    }

    public CalculatorMACD() throws IOException {
        this(1, 0.20);

    }

    public enum MACDState {
        POSITIVE_ABOVE_THRESHOLD, POSITIVE_BELOW_THRESHOLD, NEGETIVE_ABOVE_THRESHOLD, NEGETIVE_BELOW_THRESHOLD, START;
    }

    public RetCode calcMACD(Instrument instrument, int index, double macd, double macdSignal, double macdHist) {
        int noOutEle = 1;
        int endIndex = instrument.getPriceList().size() - 1;
        int startIndex = endIndex - noOutEle + 1;
        double[] macdResult = new double[endIndex + 1];
        double[] macdHistResult = new double[endIndex + 1];
        double[] macdSignalResult = new double[endIndex + 1];
        double[] closePriceInput = new double[endIndex + 1];
        MInteger strtOutIndex = new MInteger();
        strtOutIndex.value = startIndex;
        MInteger outNb = new MInteger();
        outNb.value = noOutEle;
        for (int i = 0; i <= endIndex; i++) {
            closePriceInput[i] = instrument.getPriceList().get(i).getClosePrice();

        }
        RetCode retCode = core.macd(startIndex, endIndex, closePriceInput, fastPeriod, slowPeriod, signalPeriod,
                strtOutIndex, outNb, macdResult, macdSignalResult, macdHistResult);
        macd = macdResult[0];
        macdHist = macdHistResult[0];
        macdSignal = macdSignalResult[0];
        return retCode;
    }

    public RetCode calcMACD(InstrumentAo instrumentAo, List<Double> macdList, List<Double> macdSignalList, List<Double> macdHistList) {
        //System.out.println("43232"+noOutEle);
        if (noOutEle == Integer.MAX_VALUE) {
            noOutEle = instrumentAo.getPriceList().size();
        }

        int endIndex = instrumentAo.getPriceList().size() - 1;
        int startIndex = endIndex - noOutEle + 1;
        double[] macdResult = new double[endIndex + 1];
        double[] macdHistResult = new double[endIndex + 1];
        double[] macdSignal = new double[endIndex + 1];
        double[] closePriceInput = new double[endIndex + 1];
        MInteger strtOutIndex = new MInteger();
        strtOutIndex.value = startIndex;
        MInteger outNb = new MInteger();
        outNb.value = noOutEle;
        for (int index = 0; index <= endIndex; index++) {
            closePriceInput[index] = instrumentAo.getPriceList().get(index).getClosePrice();

        }
        RetCode retCode = core.macd(startIndex, endIndex, closePriceInput, fastPeriod, slowPeriod, signalPeriod,
                strtOutIndex, outNb, macdResult, macdSignal, macdHistResult);
        for (int i = 0; i <= endIndex; i++) {
            macdList.add(macdResult[i]);
            macdSignalList.add(macdSignal[i]);
            macdHistList.add(macdHistResult[i]);
        }
        //result=macdHistResult[endIndex];
        return retCode;
    }

    public MACDState getMACDState(Double result) {

        // RetCode retCode=calcMACD(instrumentVo);
        //  if(retCode.equals(RetCode.Success)){
        if (result > threshold) {
            return MACDState.POSITIVE_ABOVE_THRESHOLD;
        } else if (result > 0) {
            return MACDState.POSITIVE_BELOW_THRESHOLD;
        } else if (result > -threshold) {
            return MACDState.NEGETIVE_ABOVE_THRESHOLD;
        } else {
            return MACDState.NEGETIVE_BELOW_THRESHOLD;
        }
       /* }else{
            throw new RuntimeException(retCode.toString());
        }*/

    }

    public void setNoOutEle(int noOutEle) {
        this.noOutEle = noOutEle;
    }
}
