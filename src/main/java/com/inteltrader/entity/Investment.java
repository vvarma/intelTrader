package com.inteltrader.entity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/2/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Investment {
    private long investmentId;
    private String symbolName;
    private Integer quantity;
    private Double currentPrice;
    private List<Transactions> transactionsList;
}
