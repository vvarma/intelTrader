package com.inteltrader.advisor.qlearning;


import com.inteltrader.entity.Instrument;
import com.inteltrader.service.InstrumentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
@ContextConfiguration(locations = {"file:src/test/resources/test-Context.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class QLearningTest {
    @Autowired
    QLearning qLearning;
    @Autowired
    InstrumentService instrumentService;
    String[] token = {"MACD", "RSI", "BBAND"};
    String symbolName = "CIPLA";

    @Test
    public void checkInitForQLearning() {
        Instrument instrument = null;
        try {
            instrument = instrumentService.retrieveInstrument(symbolName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        System.out.println("token" + token);
        qLearning.initWrapper(instrument, token);
        Assert.assertEquals(qLearning.getWrapper().getInstrument().getSymbolName(), symbolName);
    }

    @Test
    public void checkGetStateBuilderForIndex() {
        Instrument instrument = null;
        try {
            instrument = instrumentService.retrieveInstrument(symbolName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        qLearning.initWrapper(instrument, token);
        int index = qLearning.getWrapper().getInstrument().getPriceList().size() - 1;
        Assert.assertEquals(qLearning.getWrapper().getStateBuilder(index).build().getClass(), State.class);
        Assert.assertEquals(qLearning.getWrapper().getStateBuilder(0).build().getClass(), State.class);
    }

    @Test(expected = IndexOutOfBoundsException.class)

    public void checkGetStateBuilderForIndexOutOfBounds() {
        Instrument instrument = null;
        try {
            instrument = instrumentService.retrieveInstrument(symbolName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        qLearning.initWrapper(instrument, token);
        int index = qLearning.getWrapper().getInstrument().getPriceList().size() - 1;
        qLearning.getWrapper().getStateBuilder(index + 1);
    }

    @Test
    public void initTrainerAndTrain() {
        Instrument instrument = null;
        try {
            instrument = instrumentService.retrieveInstrument(symbolName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        qLearning.initWrapper(instrument, token);
        QLearning.Trainer trainer = qLearning.new Trainer(0.15, 0.9999, 40, 0.15);
        Set<State> stateSet = trainer.initTrain();
        System.out.println(stateSet);

    }


}
