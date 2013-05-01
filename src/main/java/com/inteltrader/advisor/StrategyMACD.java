package com.inteltrader.advisor;

import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;
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
 * User: Vinay
 * Date: 6/4/13
 * Time: 6:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class StrategyMACD extends Strategy {

    private Core core=new Core();
    private Properties properties=new Properties();
    private int fastPeriod,slowPeriod,signalPeriod;
    private RetCode calcMACD(int fastPeriod,int slowPeriod,int signalPeriod, int noOutEle,List<Double> macdList,List<Double> macdSignalList,List<Double> macdHistList){
        int endIndex=getInstrumentVo().getPriceList().size()-1;
        int startIndex=endIndex-noOutEle+1;
        double [] macdResult=new double[endIndex+1];
        double [] macdHistResult=new double[endIndex+1];
        double [] macdSignal=new double[endIndex+1];
        double [] closePriceInput=new double[endIndex+1];
        MInteger strtOutIndex=new MInteger();
        strtOutIndex.value=startIndex;
        MInteger outNb=new MInteger();
        outNb.value=noOutEle;
        for (int index=0;index<=endIndex;index++){
            closePriceInput[index]=getInstrumentVo().getPriceList().get(index).getClosePrice();

        }
        RetCode retCode=core.macd(startIndex, endIndex, closePriceInput, fastPeriod, slowPeriod,signalPeriod,
                strtOutIndex, outNb, macdResult,macdSignal,macdHistResult);
        for (int i=0;i<=endIndex-fastPeriod;i++){
            macdList.add(macdResult[i]);
            macdSignalList.add(macdSignal[i]);
            macdHistList.add(macdHistResult[i]);
        }
        return retCode;
    }


    @Override
    public Advice getStrategicAdvice() {
        List<Double> macdList=new ArrayList<Double>();
        List<Double> macdSignalList=new ArrayList<Double>();
        List<Double> macdHistList=new ArrayList<Double>();
        calcMACD(fastPeriod,slowPeriod,signalPeriod,14,macdList,macdSignalList,macdHistList);
        macdList=macdList.subList(0,14);
        macdSignalList=macdSignalList.subList(0,14);
        macdHistList=macdHistList.subList(0,14);
        if (macdHistList.get(0)>0)
            return Advice.BUY;
        else if (macdHistList.get(0)<0)
            return Advice.SELL;
        else
            return Advice.HOLD;


    }
    public StrategyMACD(Instrument instrument) throws IOException {
        super(instrument);
        properties.load(new FileInputStream("intel.properties"));
        fastPeriod=Integer.parseInt(properties.getProperty("MACD_FastPeriod"));
        slowPeriod=Integer.parseInt(properties.getProperty("MACD_SlowPeriod"));
        signalPeriod=Integer.parseInt(properties.getProperty("MACD_SignalPeriod"));
    }


}

