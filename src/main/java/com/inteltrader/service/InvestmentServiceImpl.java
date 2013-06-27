package com.inteltrader.service;

import com.inteltrader.advisor.Advice;
import com.inteltrader.entity.Investment;
import com.inteltrader.entity.Transactions;
import org.apache.log4j.Logger;
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
    private Logger logger=Logger.getLogger(this.getClass());
    @Override
    public void makeInvestment(Advice advice, Investment investment) {
        logger.debug("Making Investment..");
        int tradeQuantity=Integer.parseInt(properties.getProperty("TRADE_QUANTITY"));
         switch (advice){
            case BUY:
                if (investment.getQuantity()<0)
                    investment.setQuantity(0);
                else
                    investment.setQuantity(investment.getQuantity()+tradeQuantity);
                investment.getTransactionsList().add(new Transactions(tradeQuantity,investment.getCurrentPrice())) ;
                break;
            case SELL:
                if (investment.getQuantity()>0)
                    investment.setQuantity(0);
                else
                    investment.setQuantity(investment.getQuantity()- tradeQuantity);
                investment.getTransactionsList().add(new Transactions(-tradeQuantity,investment.getCurrentPrice())) ;
                break;
            default:break;


        }
        logger.debug("Investment :"+investment.getSymbolName() +" advice :"+advice+
                " present quantity :"+investment.getQuantity()+" present price :"+investment.getCurrentPrice().getClosePrice());
    }

    public InvestmentServiceImpl() throws IOException {
        properties.load(new FileInputStream("intel.properties"));
    }
}
