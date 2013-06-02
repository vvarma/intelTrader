package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.InstrumentAo;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/27/13
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
public  class TAWrapper implements InstrumentWrapper  {
    private InstrumentWrapper instrumentWrapper;
    private String desc;

    protected TAWrapper(InstrumentWrapper instrumentWrapper,String desc) {
        this.instrumentWrapper = instrumentWrapper;
        this.desc=desc;
    }

    @Override
    public void updateWrapper(Price price) {
        instrumentWrapper.updateWrapper(price);

    }

    @Override
    public InstrumentAo getInstrument() {
        return instrumentWrapper.getInstrument();
    }
    public TAWrapper getWrapper(){
        return this;
    }
    public static TAWrapper WrapMaker(Instrument instrument,String ...tokens) throws IOException {
        TAWrapper taWrapper=new TAWrapper(new InstrumentWrapperImpl(instrument),"Default");
        List<String> tokenList= Arrays.asList(tokens);
        if(tokenList.contains("MACD"))  {
            taWrapper=new MACDWrapper(taWrapper,"MACD");
        }
        return taWrapper;
    }

    public String getDesc() {
        return desc;
    }
}
