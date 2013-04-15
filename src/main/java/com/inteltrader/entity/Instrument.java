package com.inteltrader.entity;



import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/2/13
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Entity
@Table(name="INSTRUMENT",schema = "TRADER_DB")
public class Instrument implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long instrumentId=1;
    private String symbolName;
    @OneToMany
    private List<Price> priceList=new ArrayList<Price>();


    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }
    public long getInstrumentId() {
        return instrumentId;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public List<Price> getPriceList() {
        return priceList;
    }

    public Instrument() {
    }

    public Instrument(String symbolName) {
        this.symbolName = symbolName;
    }
}
