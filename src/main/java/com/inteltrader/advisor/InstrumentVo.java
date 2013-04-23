package com.inteltrader.advisor;



import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/2/13
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */

public class InstrumentVo {

    private String symbolName;
    private List<Price> priceList;


  public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }
       public String getSymbolName() {
        return symbolName;
    }

    public List<Price> getPriceList() {
        return priceList;
    }

    public InstrumentVo(Instrument instrument  ) {
        priceList=instrument.getPriceList();
        symbolName=instrument.getSymbolName();
    }


}
