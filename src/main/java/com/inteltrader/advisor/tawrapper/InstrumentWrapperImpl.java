package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.InstrumentAo;
import com.inteltrader.entity.Instrument;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/27/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class InstrumentWrapperImpl implements InstrumentWrapper {
    private InstrumentAo instrumentAo;

    public InstrumentWrapperImpl(Instrument instrument) {
        instrumentAo=new InstrumentAo();
        this.instrumentAo.setSymbolName(instrument.getSymbolName());
        this.instrumentAo.setPriceList(instrument.getPriceList());
    }
    public InstrumentWrapperImpl(InstrumentAo instrumentAo) {
        this.instrumentAo = instrumentAo;
    }

    @Override
    public InstrumentAo getInstrument() {
        return instrumentAo;
    }
}
