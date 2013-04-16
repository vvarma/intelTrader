package com.inteltrader.entity;



import org.hibernate.annotations.IndexColumn;
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
    @Column(name = "INSTRUMNET_ID")
    private long instrumentId=1;
    private String symbolName;
    @ElementCollection(targetClass=com.inteltrader.entity.Price.class)
    @JoinTable(
            name="INSTRUMENT_PRICE",
            schema="TRADER_DB",
            joinColumns=@JoinColumn(name="INSTRUMNET_ID")
    )
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

    @Override
    public String toString() {
        return "Instrument{" +
                "instrumentId=" + instrumentId +
                ", symbolName='" + symbolName + '\'' +
                ", priceList=" + priceList +
                '}';
    }
}
