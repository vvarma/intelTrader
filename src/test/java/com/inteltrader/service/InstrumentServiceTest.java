package com.inteltrader.service;

import com.inteltrader.entity.Instrument;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 8/28/13
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/test-Context.xml"})
public class InstrumentServiceTest {
    @Autowired
    InstrumentServiceImpl instrumentServiceImpl;
    @Test
    public void testGetSingleInstrument(){
        Calendar strt=new GregorianCalendar(2013,01,01);
        Calendar end=new GregorianCalendar(2013,01,16);
        String symbol="CIPLA";
        Instrument instrument=null;


            try {
                instrument= instrumentServiceImpl.getSingleInstrumentGivenDateAndName(strt,end,symbol);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        System.out.println(instrument.getCurrentPrice().getTimeStamp());
        Assert.assertEquals(end,instrument.getCurrentPrice().getTimeStamp());

    }

}
