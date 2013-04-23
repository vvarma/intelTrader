package com.inteltrader.advisor;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 6/4/13
 * Time: 1:25 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Strategy implements Strategies {

    private InstrumentVo instrumentVo;

    public InstrumentVo getInstrumentVo() {
        return instrumentVo;
    }

    public void setInstrumentVo(InstrumentVo instrumentVo) {
        this.instrumentVo = instrumentVo;
    }

}
