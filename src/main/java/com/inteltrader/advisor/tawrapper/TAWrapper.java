package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.InstrumentAo;
import com.inteltrader.advisor.qlearningadvisor.State;
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
public abstract class TAWrapper implements InstrumentWrapper  {
    private InstrumentWrapper instrumentWrapper;
    private String desc;

    protected TAWrapper(InstrumentWrapper instrumentWrapper,String desc) {
        this.instrumentWrapper = instrumentWrapper;
        this.desc=desc;
    }

    @Override
    public InstrumentAo getInstrument() {
        return instrumentWrapper.getInstrument();
    }
    public InstrumentWrapper getWrapper(){
        return instrumentWrapper;
    }
    public static InstrumentWrapper WrapMaker(Instrument instrument,String ...tokens) throws IOException {
        InstrumentWrapper instrumentWrapper=new InstrumentWrapperImpl(instrument);
        List<String> tokenList= Arrays.asList(tokens);
        if(tokenList.contains("MACD"))  {
            instrumentWrapper=new MACDWrapper(instrumentWrapper,"MACD");
        }
        return instrumentWrapper;
    }

    public String getDesc() {
        return desc;
    }
}
