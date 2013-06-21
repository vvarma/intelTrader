package com.inteltrader.advisor;



import com.inteltrader.entity.Price;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/2/13
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */

public class InstrumentAo implements Serializable {

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

    public InstrumentAo() {
    }

    public InstrumentAo(String symbolName) {
        this.symbolName = symbolName;
    }
    public Price getCurrentPrice(){
        int index=priceList.size()-1;
        return priceList.get(index);
    }
}
