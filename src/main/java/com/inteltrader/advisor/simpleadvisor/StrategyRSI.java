package com.inteltrader.advisor.simpleadvisor;

import com.inteltrader.advisor.Advice;
import com.inteltrader.entity.Instrument;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/29/13
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class StrategyRSI extends Strategy {
    private Core core=new Core();
    private Properties properties=new Properties();
    private int rsiPeriod;

    private RetCode calcRSI(int period,int noOutEle,List<Double> resultList){

        int endIndex=getInstrumentVo().getPriceList().size()-1;
        int startIndex=endIndex-noOutEle+1;
        double [] outResult=new double[endIndex+1];
        double [] closePriceInput=new double[endIndex+1];
        MInteger strtOutIndex=new MInteger();
        strtOutIndex.value=startIndex;
        MInteger outNb=new MInteger();
        outNb.value=noOutEle;
        for (int index=0;index<=endIndex;index++){
            closePriceInput[index]=getInstrumentVo().getPriceList().get(index).getClosePrice();

        }
        RetCode retCode=core.rsi(startIndex, endIndex, closePriceInput, period,
                strtOutIndex, outNb, outResult);
        for (int i=0;i<=endIndex-period;i++){
            resultList.add(outResult[i]);
        }
        return retCode;
    }
    @Override
    public Advice getStrategicAdvice() {
        List<Double> rsiList=new ArrayList<Double>();
        calcRSI(rsiPeriod, 14,rsiList);
        rsiList=rsiList.subList(0,14);
        if (rsiList.get(0)>70){
            return Advice.SELL;
        }else if (rsiList.get(0)<30){
            return Advice.BUY;
        }
        return Advice.HOLD;
    }

    public StrategyRSI(Instrument instrument) throws IOException {
        super(instrument);
        properties.load(new FileInputStream("intel.properties"));
        rsiPeriod=Integer.parseInt(properties.getProperty("RSI_Period"));
    }
}
