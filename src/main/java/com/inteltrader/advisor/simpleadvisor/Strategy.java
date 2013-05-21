package com.inteltrader.advisor.simpleadvisor;

import com.inteltrader.advisor.InstrumentAo;
import com.inteltrader.entity.Instrument;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 6/4/13
 * Time: 1:25 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Strategy implements Strategies {

    private InstrumentAo instrumentVo;

    public InstrumentAo getInstrumentVo() {
        return instrumentVo;
    }

    public void setInstrumentVo(InstrumentAo instrumentVo) {
        this.instrumentVo = instrumentVo;
    }

    protected Strategy(Instrument instrument) {
        instrumentVo=new InstrumentAo(instrument.getSymbolName());
        instrumentVo.setPriceList(instrument.getPriceList());

    }
}
