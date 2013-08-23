package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.InstrumentWrapper;
import com.inteltrader.advisor.qlearning.State;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;
import com.inteltrader.indicators.CalculatorMACD;
import com.inteltrader.service.InstrumentService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 6/5/13
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/test-Context.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TAWrapperTest {
    @Autowired
    InstrumentService instrumentService;

    @Test
    public void shouldRetrieveInstrumentAndCreateWrapperCheckBySymbolName() throws NoSuchFieldException {
        Instrument instrument = instrumentService.retrieveInstrument("CIPLA");
        InstrumentWrapper wrapper = TAWrapper.WrapMaker(instrument, "");
        Assert.assertEquals(wrapper.getInstrument().getSymbolName(), instrument.getSymbolName());
    }

    @Test
    public void shouldRetrieveInstrumentCreateWrapperAddPriceAndGetState() throws NoSuchFieldException {
        Instrument instrument = instrumentService.retrieveInstrument("CIPLA");
        try {
            InstrumentWrapper wrapper = TAWrapper.WrapMaker(instrument, "MACD");
            Price price = wrapper.getInstrument().getCurrentPrice();
            wrapper.getInstrument().getPriceList().remove(wrapper.getInstrument().getPriceList().size() - 1);
            State preState = new State.Builder().build();
            State state = wrapper.updateWrapperAndGetStateBuilder(price).build();
            System.out.println(state);
            Assert.assertEquals(wrapper.getInstrument().getSymbolName(), instrument.getSymbolName());


        } catch (IOException e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void generalTestForSet() {
        State state = new State.Builder().macd(CalculatorMACD.MACDState.NEGETIVE_BELOW_THRESHOLD).build();
        Set<State> stateSet = new HashSet<State>();
        stateSet.add(state);
        state.updateReward(Advice.BUY, 12);
        State nState = new State.Builder().macd(CalculatorMACD.MACDState.NEGETIVE_BELOW_THRESHOLD).build();
        nState.updateReward(Advice.SELL, 21);
        stateSet.add(nState);
        Iterator iter = stateSet.iterator();
        while (iter.hasNext()) {
            State s = (State) iter.next();
            System.out.println("12332" + s);
            System.out.println("12332" + nState);
            Assert.assertEquals(s, state);

        }
    }

/*    @Test
    public void forUpdateWrapper() {
        Instrument instrument = instrumentService.retrieveInstrument("CIPLA");
        try {
            Price price = instrument.getCurrentPrice();
            instrument.getPriceList().remove(instrument.getPriceList().size() - 1);
            InstrumentWrapper wrapper = TAWrapper.WrapMaker(instrument, "MACD");
            Set<State> stateSet = new HashSet<State>();
            State preState = new State.Builder().build();
            stateSet.add(preState);
            wrapper.updateHoldings(Advice.BUY);
            System.out.println(preState);
            preState = wrapper.updateWrapperAndGetStateBuilder(price, preState, Advice.BUY).build();

            Iterator iter = stateSet.iterator();
            while (iter.hasNext()) {
                State s = (State) iter.next();
                Assert.assertNotSame(s, preState);
                System.out.println(preState);

                System.out.println(s);
            }
            Assert.assertEquals(wrapper.getInstrument().getPortfolioName(), instrument.getPortfolioName());
            Assert.assertEquals(1, stateSet.size());


        } catch (IOException e) {
            Assert.assertTrue(false);
        }
    }*/
    /*@Test
    public void isTheStateBuilderWorking(){
        Instrument instrument = instrumentService.retrieveInstrument("CIPLA");

    }*/

}
