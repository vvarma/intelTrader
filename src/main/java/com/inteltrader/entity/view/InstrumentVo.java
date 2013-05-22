package com.inteltrader.entity.view;

import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/22/13
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class InstrumentVo {
    private String symbolName;
    private Date startDate;
    private Date endDate;
    private List<PriceVo> priceList;

    public InstrumentVo(Instrument instrument) {
        symbolName=instrument.getSymbolName();
        int index=instrument.getPriceList().size()-1;
        startDate=instrument.getPriceList().get(0).getTimeStamp().getTime();
        endDate=instrument.getPriceList().get(index).getTimeStamp().getTime();
        priceList=new ArrayList<PriceVo>();
        for (Price price:instrument.getPriceList()){
            Date date=price.getTimeStamp().getTime();
            priceList.add(new PriceVo(date,price.getClosePrice(),price.getOpenPrice(),price.getLowPrice(),price.getHighPrice(),price.getLastClosePrice(),price.getTotalTradedQuantity()));
        }
    }

    public String getSymbolName() {
        return symbolName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<PriceVo> getPriceList() {
        return priceList;
    }
}
