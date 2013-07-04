package com.inteltrader.entity.view;

import com.inteltrader.advisor.tawrapper.*;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;

import java.util.*;

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
    private Map<String,List<Double>> extras;

    public InstrumentVo(Instrument instrument) {
        extras=new HashMap<String, List<Double>>();
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

    public InstrumentVo(InstrumentWrapper wrapper) {
        extras=new HashMap<String, List<Double>>();
        symbolName=wrapper.getInstrument().getSymbolName();
        startDate=wrapper.getInstrument().getPriceList().get(0).getTimeStamp().getTime();
        int index=wrapper.getInstrument().getPriceList().size()-1;
        endDate=wrapper.getInstrument().getPriceList().get(index).getTimeStamp().getTime();
        priceList=new ArrayList<PriceVo>();
        for (Price price:wrapper.getInstrument().getPriceList()){
            Date date=price.getTimeStamp().getTime();
            priceList.add(new PriceVo(date,price.getClosePrice(),price.getOpenPrice(),price.getLowPrice(),price.getHighPrice(),price.getLastClosePrice(),price.getTotalTradedQuantity()));
        }
        while (wrapper instanceof TAWrapper){
            TAWrapper wrap=(TAWrapper)wrapper;
            String desc=wrap.getDesc();
            if (desc.equals("MACD")){
                extras.put("MACD",((MACDWrapper)wrapper).getMacdList());
                extras.put("MACDHist",((MACDWrapper)wrapper).getMacdHistList());
                extras.put("MACDSignal",((MACDWrapper)wrapper).getMacdSignalList());
            }
            if (desc.equals("RSI")){
                extras.put("RSI",((RSIWrapper)wrap).getRsiList());
            }
            if (desc.equals("BBAND")){
                extras.put("BBANDUpper",((BBandWrapper)wrap).getUpperBand());
                extras.put("BBANDLower",((BBandWrapper)wrap).getLowerBand());
                extras.put("BBANDMiddle",((BBandWrapper)wrap).getMiddleBand());
            }
            wrapper=wrap.getWrapper();
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
