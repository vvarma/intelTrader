package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.InstrumentAo;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/27/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class InstrumentWrapperImpl implements InstrumentWrapper {
    private InstrumentAo instrumentAo;
    private int MAX_SIZE=2* 365;

    public InstrumentWrapperImpl(Instrument instrument) {
        instrumentAo=new InstrumentAo();
        this.instrumentAo.setSymbolName(instrument.getSymbolName());
        if (instrument.getPriceList().size()<MAX_SIZE){
            this.instrumentAo.setPriceList(instrument.getPriceList());
        }else{
            int LIST_SIZE=instrument.getPriceList().size();
            this.instrumentAo.setPriceList(instrument.getPriceList().subList(LIST_SIZE-MAX_SIZE,LIST_SIZE));
        }


    }
    public InstrumentWrapperImpl(InstrumentAo instrumentAo) {
        this.instrumentAo = instrumentAo;
    }

    @Override
    public void updateWrapper(Price price) {
        if (instrumentAo.getPriceList().size()<MAX_SIZE){
            instrumentAo.getPriceList().add(price);
        }                                          else {
            instrumentAo.getPriceList().remove(0);
            instrumentAo.getPriceList().add(price);
        }
    }

    @Override
    public InstrumentAo getInstrument() {
        return instrumentAo;
    }
}
