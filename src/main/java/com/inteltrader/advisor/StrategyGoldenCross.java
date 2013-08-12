package com.inteltrader.advisor;

import com.inteltrader.calculator.SMA;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/23/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class StrategyGoldenCross extends Strategy {
    private Properties properties=new Properties();

    @Override
    public Advice getStrategicAdvice() {
        int shortPeriod=Integer.getInteger(properties.getProperty("GoldenCross_shortPeriod"));
        int longPeriod=Integer.getInteger(properties.getProperty("GoldenCross_longPeriod"));
        double shortPeriodSmaResult= SMA.calculateSMA(shortPeriod,getInstrumentVo().getPriceList());
        double longPeriodSmaResult= SMA.calculateSMA(longPeriod,getInstrumentVo().getPriceList());

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
    public StrategyGoldenCross() throws IOException {
        properties.load(new FileInputStream("intel.properties"));
    }
}
