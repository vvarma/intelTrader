package com.inteltrader.advisor.tawrapper2;

import com.inteltrader.entity.Instrument;
import com.inteltrader.service.InstrumentService;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 7/18/13
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class TAWrapperTest {
    @Autowired
    InstrumentService instrumentService;

    @Test
    public void shouldMakeMACDWrapper() throws NoSuchFieldException, IOException {
        Instrument instrument=instrumentService.retrieveInstrument("CIPLA");
        InstrumentWrapper wrapper=TAWrapper.wrapMaker(instrument,"MACD");
        boolean bool=false;
        while (!(wrapper instanceof InstrumentWrapperImpl)){
            if (wrapper instanceof TAWrapper){
                if(((TAWrapper)wrapper).getDesc().equals("MACD")){
                    bool=true;
                }
                wrapper=((TAWrapper) wrapper).getWrapper();
            }

        }
        System.out.println(wrapper.getStateBuilder(100).build());
        Assert.assertTrue(bool);
    }

    @Test
    public void checkCalc() throws NoSuchFieldException {
        int index=112;
       Core core =new Core();
        int fastPeriod=12;
        int slowPeriod=24;
        int signalPeriod=9;
        int noOutEle=1;
        double threshold=1;
        double result;
        Instrument instrument=instrumentService.retrieveInstrument("CIPLA");
        if (index>=instrument.getPriceList().size())
            index=instrument.getPriceList().size()-1;
        int endIndex = index;
        int startIndex = 0;
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
        for (double a:macdHistResult)
         System.out.println(a);
    }
}
