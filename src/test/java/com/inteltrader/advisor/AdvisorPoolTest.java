package com.inteltrader.advisor;

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
 * Date: 7/15/13
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class AdvisorPoolTest  {
    @Autowired
    AdvisorPool advisorPool;


    public void checkMultipleAdvisor() throws NoSuchFieldException, IOException {
        Calendar cal=new GregorianCalendar();
        cal.set(2013,06,15);
        Global.setCalendar(cal);
        Advisor advisor=advisorPool.getAdvisor("CIPLA#MACD#RSI");
        Advisor advisor1=advisorPool.getAdvisor("SBIN#MACD#RSI");
        Assert.assertNotNull(advisor);
        Assert.assertNotNull(advisor1);
        Assert.assertNotSame(advisor,advisor1);

    }


}
