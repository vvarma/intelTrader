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

    private List<Double> calcSma(int period,int noOutEle){
        List<Double> resultList=new ArrayList<Double>();
        int endIndex=getInstrumentVo().getPriceList().size()-1;
        int startIndex=endIndex-noOutEle+1;
        double [] shortOutResult=new double[endIndex+1];
        double [] closePriceInput=new double[endIndex+1];
        MInteger strtOutIndex=new MInteger();
        strtOutIndex.value=startIndex;
        MInteger outNb=new MInteger();
        outNb.value=noOutEle;
        for (int index=0;index<=endIndex;index++){
            closePriceInput[index]=getInstrumentVo().getPriceList().get(index).getClosePrice();

        }
        RetCode retCode=core.sma    (startIndex,endIndex,closePriceInput,period,
                strtOutIndex,outNb,shortOutResult);
        for (int i=0;i<=endIndex-period;i++){
           resultList.add(shortOutResult[i]);
        }
        return resultList;
    }
    @Override
    public Advice getStrategicAdvice() {
        List<Double> shortSmaList=calcSma(shortPeriod,300).subList(0,300);
        List<Double> longSmaList=calcSma(longPeriod,300).subList(0,300);
        System.out.println(shortSmaList);
        System.out.println(longSmaList);
        for (int i=0;i<300;i++){

        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public StrategyGoldenCross(Instrument instrument) throws IOException {
        super(instrument);
        properties.load(new FileInputStream("intel.properties"));
        shortPeriod=Integer.valueOf(properties.getProperty("GoldenCross_SHORT_PERIOD"));
        longPeriod=Integer.valueOf(properties.getProperty("GoldenCross_LONG_PERIOD"));
    }

}
