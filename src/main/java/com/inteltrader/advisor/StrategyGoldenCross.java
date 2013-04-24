package com.inteltrader.advisor;

import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;

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

    @Override
    public Advice getStrategicAdvice() {

        int startIndex=0;
        int endIndex=getInstrumentVo().getPriceList().size()-1;
        double [] shortOutResult=new double[endIndex+1];
        double [] closePriceInput=new double[endIndex+1];
        MInteger strtOutIndex=new MInteger();
        strtOutIndex.value=startIndex;
        MInteger outNb=new MInteger();
        outNb.value=endIndex-startIndex;
        for (int index=0;index<=endIndex;index++){
            closePriceInput[index]=getInstrumentVo().getPriceList().get(index).getClosePrice();

        }
        RetCode retCode=core.sma    (startIndex,endIndex,closePriceInput,shortPeriod,
                strtOutIndex,outNb,shortOutResult);

        System.out.println("res"+ Arrays.asList(shortOutResult));
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public StrategyGoldenCross(Instrument instrument) throws IOException {
        super(instrument);
        properties.load(new FileInputStream("intel.properties"));
        shortPeriod=Integer.getInteger(properties.getProperty("GoldenCross_SHORT_PERIOD"));
        longPeriod=Integer.getInteger(properties.getProperty("GoldenCross_LONG_PERIOD"));
    }

}
