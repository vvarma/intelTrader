package com.inteltrader.advisor;


<<<<<<< HEAD:src/main/java/com/inteltrader/advisor/InstrumentAo.java
import com.inteltrader.entity.Price;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
=======

import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;

>>>>>>> a52d2c21c50f2d5fe0ce3e8d7299acd27b9df49e:src/main/java/com/inteltrader/advisor/InstrumentVo.java
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/2/13
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */

<<<<<<< HEAD:src/main/java/com/inteltrader/advisor/InstrumentAo.java
public class InstrumentAo implements Serializable {
=======
public class InstrumentVo {
>>>>>>> a52d2c21c50f2d5fe0ce3e8d7299acd27b9df49e:src/main/java/com/inteltrader/advisor/InstrumentVo.java

    private String symbolName;
    private List<Price> priceList;


  public void setPriceList(Collection<Price> priceList) {

        this.priceList = new ArrayList<Price>(priceList);
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

<<<<<<< HEAD:src/main/java/com/inteltrader/advisor/InstrumentAo.java
    public InstrumentAo() {
    }

    public InstrumentAo(String symbolName) {
        this.symbolName = symbolName;
    }
    public Price getCurrentPrice(){
        int index=priceList.size()-1;
        return priceList.get(index);
    }
=======
    public InstrumentVo(Instrument instrument  ) {
        priceList=instrument.getPriceList();
        symbolName=instrument.getSymbolName();
    }


>>>>>>> a52d2c21c50f2d5fe0ce3e8d7299acd27b9df49e:src/main/java/com/inteltrader/advisor/InstrumentVo.java
}
