package com.inteltrader.advisor.qlearningadvisor;

import com.inteltrader.advisor.Advisor;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.States;
import com.inteltrader.service.InstrumentService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Objects;

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
            Advisor advisor=new QLearningAdvisorImpl2(instrument, Holdings.HoldingState.NO_HOLDING,"MACD");
            System.out.println("123"+((QLearningAdvisorImpl2)advisor).getStateSet());
            //Assert.assertNotNull(advisor.getAdvice());
        }catch (IOException e){
            Assert.assertTrue(false);
        }
    }
   @Test
    public void testForCheckingStates(){
        Instrument instrument=instrumentService.retrieveInstrument("CIPLA");
        try{
            Advisor advisor1=new QLearningAdvisorImpl2(instrument, Holdings.HoldingState.NO_HOLDING,"MACD","RSI");
            Advisor advisor2=new QLearningAdvisorImpl2(instrument, Holdings.HoldingState.NO_HOLDING,"MACD","RSI");
            System.out.println("123"+((QLearningAdvisorImpl2)advisor1).getStates());
            System.out.println("123"+((QLearningAdvisorImpl2)advisor2).getStates());
            Assert.assertTrue(checkEqualStates(advisor1.getStates(),advisor2.getStates()));
        }catch (IOException e){
            Assert.assertTrue(false);
        }
    }

    private boolean checkEqualStates(States states, States states1) {
        if (!states.getSymbolNamme().equals(states1.getSymbolNamme())) {
            System.out.println("name mismatch");
            return false;
        }
        boolean bool=true;
        for (State s:states.getStateSet()){
            for (State t:states1.getStateSet()){
                if (s.equals(t)){
                    if (s.getGreedyAdvice()!=t.getGreedyAdvice()) {
                        System.out.println(s);
                        System.out.println(t);
                        System.out.println("states not equal");
                        bool= false;
                    }

                }
            }
        }
        return bool;
    }
}
