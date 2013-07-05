package com.inteltrader.advisor.newqlearning;

import com.inteltrader.advisor.qlearningadvisor.State;
import com.inteltrader.entity.Instrument;
import com.inteltrader.service.InstrumentService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 7/2/13
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */

// how to check the stateless nature?

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class QLearningTest {
    @Autowired
    QLearning qLearning;
    @Autowired
    InstrumentService instrumentService;
    String[] token={"MACD","RSI","BBAND"};
    String symbolName="AMTEKAUTO";

    @Test
    public void checkInitForQLearning(){
        Instrument instrument=instrumentService.retrieveInstrument(symbolName);
        try{
            try {
                System.out.println("token" + token);
                qLearning.initWrapper(instrument,token);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            Assert.assertEquals(qLearning.getWrapper().getInstrument().getSymbolName(), symbolName);
        }catch (IOException e){
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
    @Test
    public void checkGetStateBuilderForIndex(){
        Instrument instrument=instrumentService.retrieveInstrument(symbolName);
        try {
            try {
                qLearning.initWrapper(instrument,token);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            int index=qLearning.getWrapper().getInstrument().getPriceList().size()-1;
            Assert.assertEquals(qLearning.getWrapper().getStateBuilder(index).build().getClass(),State.class);
            Assert.assertEquals(qLearning.getWrapper().getStateBuilder(0).build().getClass(),State.class);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Assert.assertTrue(false);
        }
    }
    @Test(expected = IndexOutOfBoundsException.class)

    public void checkGetStateBuilderForIndexOutOfBounds(){
       Instrument instrument=instrumentService.retrieveInstrument(symbolName);
        try {
            try {
                qLearning.initWrapper(instrument,token);
            }  catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            int index=qLearning.getWrapper().getInstrument().getPriceList().size()-1;
            qLearning.getWrapper().getStateBuilder(index+1);
        }catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Assert.assertTrue(false);
        }
    }

    @Test
    public void initTrainerAndTrain(){
        Instrument instrument=instrumentService.retrieveInstrument(symbolName);
        try {
            try {
                qLearning.initWrapper(instrument,token);
            }  catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            QLearning.Trainer trainer= qLearning.new Trainer(0.15,0.9999,40);
            Set<State> stateSet=trainer.initTrain();
            System.out.println(stateSet);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Assert.assertTrue(false);
        }
    }


}
