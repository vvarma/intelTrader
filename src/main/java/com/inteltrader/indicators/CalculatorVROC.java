package com.inteltrader.indicators;

import com.inteltrader.advisor.InstrumentAo;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.RetCode;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 8/6/13
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorVROC {
    private Core core=new Core();

    public enum ROCState{

    }
    public RetCode calcROC(InstrumentAo instrumentAo){
        return RetCode.Success;
    }
    public ROCState getROCState(){
        return null;
    }
}
