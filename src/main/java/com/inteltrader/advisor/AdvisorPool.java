package com.inteltrader.advisor;

import com.inteltrader.advisor.newqlearning.QLearning;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;
import com.inteltrader.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 7/12/13
 * Time: 12:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdvisorPool {
    private Map<String,Advisor> advisorMap=new HashMap<String, Advisor>();
    @Autowired
    private InstrumentService instrumentService;

    public Advisor getAdvisor(String key) throws NoSuchFieldException, IOException {
        Advisor advisor=advisorMap.get(key);
        if (advisor!=null)
            return advisor;
        advisor=(Advisor)new ClassPathXmlApplicationContext().getBean("advisor");
        //advisor=new QLearning(0.15,0.9999,40);
        String[] keys=key.split("#");
        String symbolName=keys[0];
        Instrument instrument=instrumentService.retrieveInstrument(symbolName);
        String[] tokens= new String[keys.length-1];
        for (int i=1;i<keys.length;i++){
            tokens[i-1]=keys[i];
        }
        System.out.println("callin initAdvisor "+instrument.getSymbolName()+ "tokens");
        for (String s:tokens){
            System.out.println(s);
        }
        advisor.initAdvisor(instrument,null,tokens);
        advisorMap.put(key,advisor);
        return advisor;
    }
    public void updateAdvisor(String key) throws NoSuchFieldException, IOException {
        Advisor advisor =getAdvisor(key);
        List<Price> newPriceList=instrumentService.getNewPrices(advisor.getWrapper().getInstrument().getSymbolName(),advisor.getWrapper().getInstrument().getCurrentPrice());
        for (Price price:newPriceList){
            advisor.updatePriceAndGetAdvice(price);
        }

    }
}
