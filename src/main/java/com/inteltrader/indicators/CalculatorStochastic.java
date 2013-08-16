package com.inteltrader.indicators;

import com.inteltrader.advisor.InstrumentAo;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 8/5/13
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorStochastic {

    private Core core;
    private Properties properties;

    public CalculatorStochastic() {
    }
    public enum StochasticState{
        OVERBOUGHT,OVERSOLD,TRENDING;
    }
    public RetCode calcStochastic(InstrumentAo instrumentAo,List<Double> kList,List<Double> dList){
        int optInFastK_Period=Integer.MIN_VALUE;
        int optInSlowK_Period=Integer.MIN_VALUE;
        int optInFastD_Period=Integer.MIN_VALUE;
        int noOutEle= instrumentAo.getPriceList().size();
        int endIndex= instrumentAo.getPriceList().size()-1;
        int startIndex=endIndex-noOutEle+1;
        double [] outKResult=new double[endIndex+1];
        double [] outDResult=new double[endIndex+1];
        double [] closePriceInput=new double[endIndex+1];
        double [] hiPriceInput=new double[endIndex+1];
        double [] lowPriceInput=new double[endIndex+1];
        MAType typeK=MAType.Sma;
        MAType typeD=MAType.Sma;
        MInteger strtOutIndex=new MInteger();
        strtOutIndex.value=startIndex;
        MInteger outNb=new MInteger();
        outNb.value=noOutEle;
        for (int index=0;index<=endIndex;index++){
            closePriceInput[index]= instrumentAo.getPriceList().get(index).getClosePrice();
            hiPriceInput[index]=instrumentAo.getPriceList().get(index).getHighPrice();
            lowPriceInput[index]=instrumentAo.getPriceList().get(index).getLowPrice();
        }
        RetCode retCode=core.stoch(startIndex, endIndex, hiPriceInput, lowPriceInput, closePriceInput, optInFastK_Period, optInSlowK_Period,
                typeK,optInFastD_Period,typeD,strtOutIndex, outNb, outKResult,outDResult);
        System.out.println("inside result rsi :"+outKResult.length);
        for (int i=0;i<=endIndex;i++){
            kList.add(outKResult[i]);
            dList.add(outDResult[i]);
        }
        return retCode;
    }
    public StochasticState getState(Double k,Double d){
        if (k>=80){
            return StochasticState.OVERBOUGHT;
        }  else if (k<=20){
            return StochasticState.OVERSOLD;
        }else {
            return StochasticState.TRENDING;
        }
    }
}
