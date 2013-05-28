package com.inteltrader.advisor.tawrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/27/13
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class MACDWrapper extends TAWrapper {
    private List<Double> macdList;
    private List<Double> macdSignalList;
    private List<Double> macdHistList;
    private CalculatorMACD calculator;
    public MACDWrapper(InstrumentWrapper instrumentWrapper,String desc) throws IOException {
        super(instrumentWrapper,desc);
        macdHistList=new ArrayList<Double>();
        macdList=new ArrayList<Double>();
        macdSignalList=new ArrayList<Double>();
        calculator=new CalculatorMACD(Integer.MAX_VALUE,1);
        //calculator=new CalculatorMACD();
        calculator.calcMACD(getInstrument(),macdList,macdSignalList,macdHistList);
        zeroBitwise();    //not a permanent solution
    }
    private void zeroBitwise(){
        int indexMacd=macdList.size()-1;
        int indexMacdHist=macdHistList.size()-1;
        int indexMacdSignal=macdSignalList.size()-1;
        if(indexMacd==indexMacdHist&&indexMacd==indexMacdSignal){
            for(int i=0;i<indexMacd;i++){
                if(macdList.get(i).equals(0.0)&macdList.get(i).equals(macdHistList.get(i))&&macdList.get(i).equals(macdSignalList.get(i))){
                    macdList.remove(i);
                    macdHistList.remove(i);
                    macdSignalList.remove(i);
                    macdList.add(0,0.0);
                    macdSignalList.add(0,0.0);
                    macdHistList.add(0,0.0);
                }
            }
        }else{
            System.err.println("Something wrong sizes should be same #123");
        }
    }
    public List<Double> getMacdList() {
        return macdList;
    }

    public List<Double> getMacdSignalList() {
        return macdSignalList;
    }

    public List<Double> getMacdHistList() {
        return macdHistList;
    }
}
