package com.inteltrader.service;

import com.inteltrader.advisor.Advice;
import com.inteltrader.util.Global;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 7/22/13
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class AnalyserServiceTest {

    @Autowired
    AnalyserService analyserService;
    @Autowired
    Global global;
    @Test
    public void shouldGetAdvice(){
        try {
            Advice advice=analyserService.getAnalysis("SBIN","MACD","BBAND","RSI");
            Assert.assertNotNull(advice);
            System.out.println(advice);
        } catch (NoSuchFieldException e) {
            Assert.assertTrue(e.getMessage(),false);
        } catch (IOException e) {
            Assert.assertTrue(e.getMessage(), false);
        }

    }
    @Test
    public void shouldGetAdviceUpdateAlso(){
        try {
            Calendar cal=new GregorianCalendar(2013,06,01);
            global.setCalendar(cal);
            Advice advice=analyserService.getAnalysis("SBIN","MACD","BBAND","RSI");
            Assert.assertNotNull(advice);
            System.out.println(advice);
            cal.set(2013,06,02);
            advice=analyserService.getAnalysis("SBIN","MACD","BBAND","RSI");
            Assert.assertNotNull(advice);
            System.out.println(advice);
        } catch (NoSuchFieldException e) {
            Assert.assertTrue(e.getMessage(),false);
        } catch (IOException e) {
            Assert.assertTrue(e.getMessage(), false);
        }

    }
}
