package com.inteltrader.advisor.tawrapper;

import com.inteltrader.advisor.InstrumentAo;
import com.inteltrader.advisor.InstrumentWrapper;
import com.inteltrader.advisor.InstrumentWrapperImpl;
import com.inteltrader.entity.Instrument;

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
public abstract class TAWrapper implements InstrumentWrapper {
    private InstrumentWrapper instrumentWrapper;


    protected TAWrapper(InstrumentWrapper instrumentWrapper) {
        this.instrumentWrapper = instrumentWrapper;

    }

    @Override
    public InstrumentAo getInstrument() {
        return instrumentWrapper.getInstrument();
    }

    public InstrumentWrapper getWrapper() {
        return instrumentWrapper;
    }

    public static InstrumentWrapper WrapMaker(Instrument instrument, String... tokens) {
        InstrumentWrapper instrumentWrapper = new InstrumentWrapperImpl(instrument);
        List<String> tokenList = Arrays.asList(tokens);
        if (tokenList.contains("MACD")) {
            instrumentWrapper = new MACDWrapper(instrumentWrapper, "MACD");
        }
        if (tokenList.contains("RSI")) {
            instrumentWrapper = new RSIWrapper(instrumentWrapper, "RSI");
        }
        if (tokenList.contains("BBAND")) {
            System.out.println("in");
            instrumentWrapper = new BBandWrapper(instrumentWrapper, "BBAND");
        }
        return instrumentWrapper;
    }

    public String getDesc() {
        return "ABstract";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TAWrapper)) return false;

        TAWrapper taWrapper = (TAWrapper) o;

        if (instrumentWrapper != null ? !instrumentWrapper.equals(taWrapper.instrumentWrapper) : taWrapper.instrumentWrapper != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return instrumentWrapper != null ? instrumentWrapper.hashCode() : 0;
    }
}
