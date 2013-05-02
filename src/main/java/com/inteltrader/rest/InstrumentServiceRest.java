package com.inteltrader.rest;

import com.google.gson.Gson;
import com.inteltrader.advisor.StrategyGoldenCross;
import com.inteltrader.entity.Instrument;
import com.inteltrader.service.InstrumentService;
import com.inteltrader.util.Global;
import com.inteltrader.util.RestCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/16/13
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/instrument")
public class InstrumentServiceRest {
    @Autowired
    private InstrumentService instrumentService;


    @RequestMapping(value = "/test/{symbol}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> testStrategyInstrument(@PathVariable("symbol") String symbolName, HttpServletRequest request) {

        Calendar strtDate=(GregorianCalendar)Global.getCalendar().clone();
        strtDate.add(Calendar.YEAR,-2);
        Instrument instrument = instrumentService.retrieveInstrument(symbolName);
        System.out.println(instrument);
        try{
            StrategyGoldenCross cross=new StrategyGoldenCross(instrument);
            cross.getStrategicAdvice();
        }catch (Exception e){
            e.printStackTrace();

        }


        return new ResponseEntity<String>("yo",
                new HttpHeaders(), HttpStatus.OK);

    }
    @RequestMapping(value = "/create/{symbol}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> createInstrument(@PathVariable("symbol") String symbolName, HttpServletRequest request) {

        Calendar strtDate=(GregorianCalendar) Global.getCalendar().clone();
        strtDate.add(Calendar.YEAR,-2);
        RestCodes responseCode = instrumentService.createInstrument(symbolName,strtDate);

        return new ResponseEntity<String>(responseCode.toString(),
                new HttpHeaders(), HttpStatus.OK);

    }
    @RequestMapping(value = "/load/{symbol}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> loadInstrument(@PathVariable("symbol") String symbolName, HttpServletRequest request){
        Instrument instrument = instrumentService.retrieveInstrument(symbolName);

        return new ResponseEntity<String>(new Gson().toJson(instrument),
                new HttpHeaders(), HttpStatus.OK);
    }
    @RequestMapping(value = "/update/{portfolioName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> updateInstruments(@PathVariable("portfolioName") String portfolioName, HttpServletRequest request){
        RestCodes responseCode = instrumentService.updateInstruments(portfolioName);

        return new ResponseEntity<String>(responseCode.toString(),
                new HttpHeaders(), HttpStatus.OK);
    }
}
