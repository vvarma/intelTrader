package com.inteltrader.advisor;

import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 24/4/13
 * Time: 7:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class StrategyGoldenCross extends Strategy {

    private int shortPeriod,longPeriod;
    private Properties properties=new Properties();
    private Core core=new Core();

    private RetCode calcSma(int period,int noOutEle,List<Double> resultList){

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
        RetCode retCode=core.sma    (startIndex,endIndex,closePriceInput,period,
                strtOutIndex,outNb,outResult);
        for (int i=0;i<=endIndex-period;i++){
           resultList.add(outResult[i]);
        }
        return retCode;
    }
    @Override
    public Advice getStrategicAdvice() {
        List<Double> shortSmaList=new ArrayList<Double>();
        List<Double> longSmaList=new ArrayList<Double>();
        calcSma(shortPeriod,14,shortSmaList);
        calcSma(longPeriod,14,longSmaList);
        shortSmaList=shortSmaList.subList(0,14);
        longSmaList=longSmaList.subList(0,14);
        System.out.println(shortSmaList);
        System.out.println(longSmaList);
        Advice advice=Advice.HOLD;
        for (int i=0;i<14;i++){
            if(shortSmaList.get(i)>longSmaList.get(i))
                advice=Advice.BUY;
            else if (shortSmaList.get(i)<longSmaList.get(i))
                advice=Advice.SELL;
        }
        return advice;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public StrategyGoldenCross(Instrument instrument) throws IOException {
        super(instrument);
        properties.load(new FileInputStream("intel.properties"));
        shortPeriod=Integer.valueOf(properties.getProperty("GoldenCross_SHORT_PERIOD"));
        longPeriod=Integer.valueOf(properties.getProperty("GoldenCross_LONG_PERIOD"));
    }

}
