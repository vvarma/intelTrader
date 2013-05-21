package com.inteltrader.entity.view;

import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/21/13
 * Time: 10:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class InstrumentVo {
    private String symbolName;
    private Date startDate;
    private Date endDate;
    private List<Double> priceList;

    private InstrumentVo(String symbolName, Date startDate, Date endDate, List<Double> priceList) {
        this.symbolName = symbolName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
    }
    public enum WhichPrice{
        CLOSE,OPEN;
    }
    public static InstrumentVo buildInstrumentVo(Instrument instrument, WhichPrice whichPrice){
        List<Double> priceList=new ArrayList<Double>();
        switch (whichPrice){
             case CLOSE:
                 for (Price price:instrument.getPriceList()){
                     priceList.add(price.getClosePrice());
                 }
                 break;
             case OPEN:
                 for (Price price:instrument.getPriceList()){
                     priceList.add(price.getOpenPrice());
                 }
                 break;
             default:
                 for (Price price:instrument.getPriceList()){
                     priceList.add(price.getClosePrice());
                 }
         }
        Date startDate=instrument.getPriceList().get(0).getTimeStamp().getTime();
        int index=instrument.getPriceList().size()-1;
        Date endDate=instrument.getPriceList().get(index).getTimeStamp().getTime();
        return new InstrumentVo(instrument.getSymbolName(),startDate,endDate,priceList);

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

    public List<Double> getPriceList() {
        return priceList;
    }
}
