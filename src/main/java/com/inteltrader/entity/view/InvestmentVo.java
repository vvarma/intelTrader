package com.inteltrader.entity.view;

import com.inteltrader.entity.Investment;
import com.inteltrader.entity.Price;
import com.inteltrader.entity.Transactions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 7/8/13
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvestmentVo {
    private String symbolName;
    private Integer quantity=0;
    private PriceVo currentPrice;
    private List<TransactionVo> transactionList;

    public String getSymbolName() {
        return symbolName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PriceVo getCurrentPrice() {
        return currentPrice;
    }

    public List<TransactionVo> getTransactionList() {
        return transactionList;
    }

    public InvestmentVo(Investment investment) {
        transactionList=new ArrayList<TransactionVo>(investment.getTransactionsList().size());
        symbolName=investment.getSymbolName();
        quantity=investment.getQuantity();
        currentPrice=new PriceVo(investment.getCurrentPrice());
        for (Transactions transactions:investment.getTransactionsList()){
            transactionList.add(new TransactionVo(transactions));
        }
    }
}
