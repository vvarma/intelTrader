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
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorRSI {
    private Core core=new Core();
    private int rsiPeriod;
    int noOutEle;
    double result;

    public enum RSIState{
        ABOVE_THRESHOLD,BETWEEN_THRESHOLD,BELOW_THRESHOLD;
    }
    public RetCode calcRSI(Instrument instrument,double rsi){
       return null;
    }
    public RetCode calcRSI(InstrumentAo instrumentAo,List<Double> resultList){
        noOutEle= instrumentAo.getPriceList().size();
        System.out.println("noutele rsi :"+noOutEle);
        int endIndex= instrumentAo.getPriceList().size()-1;
        int startIndex=endIndex-noOutEle+1;
        double [] outResult=new double[endIndex+1];
        double [] closePriceInput=new double[endIndex+1];
        MInteger strtOutIndex=new MInteger();
        strtOutIndex.value=startIndex;
        MInteger outNb=new MInteger();
        outNb.value=noOutEle;
        for (int index=0;index<=endIndex;index++){
            closePriceInput[index]= instrumentAo.getPriceList().get(index).getClosePrice();

        }
        RetCode retCode=core.rsi(startIndex, endIndex, closePriceInput, rsiPeriod,
                strtOutIndex, outNb, outResult);
       // result=outResult[endIndex];
        System.out.println("inside result rsi :"+outResult.length);
        for (int i=0;i<=endIndex;i++){
            resultList.add(outResult[i]);
        }
        System.out.println("inside result rsi 2 :"+resultList.size());
         return retCode;
    }

    public CalculatorRSI(int noOutEle) {
        rsiPeriod=14;
        this.noOutEle=noOutEle;
    }
    public RSIState getRSIState(Double result){
       /* RetCode retCode=calcRSI(instrumentVo);
        if (retCode.equals(RetCode.Success)){*/
            if (result>65){
              return RSIState.ABOVE_THRESHOLD;
            }else if (result>35){
                return RSIState.BETWEEN_THRESHOLD;
            }else{
                return RSIState.BELOW_THRESHOLD;
            }
       /* }else{
            throw new RuntimeException(retCode.toString());
        }*/
    }

    public void setNoOutEle(int noOutEle) {
        this.noOutEle = noOutEle;
    }
}
