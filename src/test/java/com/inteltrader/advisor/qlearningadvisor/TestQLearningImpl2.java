package com.inteltrader.advisor.qlearningadvisor;

import com.inteltrader.advisor.Advisor;
import com.inteltrader.entity.Instrument;
import com.inteltrader.service.InstrumentService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 6/6/13
 * Time: 4:06 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class TestQLearningImpl2 {

    @Autowired
    InstrumentService instrumentService;
    @Test
    public void testForTrainAndInit(){
        Instrument instrument=instrumentService.retrieveInstrument("CIPLA");
        try{
            Advisor advisor=new QLearningAdvisorImpl2(instrument,"MACD");
            System.out.println("123"+((QLearningAdvisorImpl2)advisor).getStateSet());
            //Assert.assertNotNull(advisor.getAdvice());
        }catch (IOException e){
            Assert.assertTrue(false);
        }
    }
}
