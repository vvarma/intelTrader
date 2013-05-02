package com.inteltrader.service;

import com.inteltrader.advisor.Advice;
import com.inteltrader.entity.Investment;
import com.inteltrader.entity.Transactions;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 21/4/13
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class InvestmentServiceImpl implements InvestmentService {
    @Autowired
    private InstrumentService instrumentService;
    private Properties properties=new Properties();
    @Override
    public void makeInvestment(Advice advice, Investment investment) {
        int tradeQuantity=Integer.parseInt(properties.getProperty("TRADE_QUANTITY"));
        System.out.println(advice.toString());
        System.out.println(this.getClass().toString());
        switch (advice){
            case BUY:
                investment.setQuantity(investment.getQuantity()+tradeQuantity );
                investment.getTransactionsList().add(new Transactions(tradeQuantity,investment.getCurrentPrice())) ;
                break;
            case SELL:
                investment.setQuantity(investment.getQuantity()-tradeQuantity );
                investment.getTransactionsList().add(new Transactions(-tradeQuantity,investment.getCurrentPrice())) ;
                break;
            default:break;


        }
    }

    public InvestmentServiceImpl() throws IOException {
        properties.load(new FileInputStream("intel.properties"));
    }
}
