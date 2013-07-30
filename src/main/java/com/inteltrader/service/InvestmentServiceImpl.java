package com.inteltrader.service;

import com.inteltrader.advisor.Advice;
import com.inteltrader.entity.Investment;
import com.inteltrader.entity.Transactions;
import com.inteltrader.util.Global;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 21/4/13
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class InvestmentServiceImpl implements InvestmentService {
    @Autowired
    Global global;
    @Autowired
    private InstrumentService instrumentService;
    private Logger logger=Logger.getLogger(this.getClass());
    @Override
    public void makeInvestment(Advice advice, Investment investment) {
        logger.debug("Making Investment..");
        int tradeQuantity=Integer.parseInt(global.getProperties().getProperty("TRADE_QUANTITY"));
        int actTradeQuantity=0;
         switch (advice){
            case BUY:
                if (investment.getQuantity()<0)
                    actTradeQuantity=tradeQuantity-investment.getQuantity();
                else
                    actTradeQuantity=tradeQuantity;

                break;
            case SELL:
                if (investment.getQuantity()>0)
                    actTradeQuantity=-tradeQuantity-investment.getQuantity();
                else
                    actTradeQuantity=-tradeQuantity;
                break;
            default:break;
        }
        investment.setQuantity(investment.getQuantity()+actTradeQuantity);
        investment.getTransactionsList().add(new Transactions(actTradeQuantity,investment.getCurrentPrice(), global.getCalendar().getTime())) ;
        logger.debug("Investment :"+investment.getSymbolName() +" advice :"+advice+
                " present quantity :"+investment.getQuantity()+" present price :"+investment.getCurrentPrice().getClosePrice());
    }

    @Override
    public double calcPnl() {

        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public InvestmentServiceImpl() {
    }
}
