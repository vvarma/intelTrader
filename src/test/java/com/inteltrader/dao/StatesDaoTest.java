package com.inteltrader.dao;

import com.inteltrader.advisor.Advice;
import com.inteltrader.entity.States;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 7/15/13
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class StatesDaoTest {
    @Autowired
    IStatesDao statesDao;

    @Test
    public void shouldReturnNullForRandomSymbolName(){
        States states=statesDao.retrieveStates("ABCDS");

        Assert.assertNull(states);
    }
    @Test
    public void shouldPersistTestStates(){
        States test=new States();
        test.setSymbolNamme("test");
        test.setPresentAdvice(Advice.BUY);
        statesDao.createState(test);
        States states=statesDao.retrieveStates("test");

        Assert.assertEquals(states,test);
    }
}
