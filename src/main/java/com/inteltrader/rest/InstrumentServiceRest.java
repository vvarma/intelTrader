package com.inteltrader.rest;

import com.google.gson.Gson;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.view.InstrumentVo;
import com.inteltrader.entity.view.InstrumentVoSelective;
import com.inteltrader.service.InstrumentService;
import com.inteltrader.util.Global;
import com.inteltrader.util.RestCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
@Controller
@RequestMapping("/instrument")
public class InstrumentServiceRest {
    @Autowired
    Global global;
    @Autowired
    private InstrumentService instrumentService;



    @RequestMapping(value = "/create/{symbol}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> createInstrument(@PathVariable("symbol") String symbolName, HttpServletRequest request) {

        Calendar strtDate=(GregorianCalendar) global.getCalendar().clone();
        strtDate.add(Calendar.YEAR,-4);
        RestCodes responseCode = instrumentService.createInstrument(symbolName,strtDate);

        return new ResponseEntity<String>(responseCode.toString(),
                new HttpHeaders(), HttpStatus.OK);

    }
    @RequestMapping(value = "/load/{symbol}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> loadInstrument(@PathVariable("symbol") String symbolName, HttpServletRequest request){
        Instrument instrument = null;
        try {
            instrument = instrumentService.retrieveInstrument(symbolName);

        InstrumentVo instrumentVo=new InstrumentVo(instrument);
        HttpHeaders headers=new HttpHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        return new ResponseEntity<String>(new Gson().toJson(instrumentVo),
                headers, HttpStatus.OK);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            HttpHeaders headers=new HttpHeaders();
            headers.add("Access-Control-Allow-Origin","*");
            return new ResponseEntity<String>("BAD SYMBOL",
                    headers, HttpStatus.BAD_REQUEST);
        }
    }



    //not required..  handled
    @RequestMapping(value = "/loadWhich/{whichPrice}/{symbol}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> loadInstrumentWhichPrice(@PathVariable("symbol") String symbolName,@PathVariable("whichPrice") String whichPrice, HttpServletRequest request){
        Instrument instrument = null;
        try {
            instrument = instrumentService.retrieveInstrument(symbolName);

        InstrumentVoSelective.WhichPrice whichPriceEnum= InstrumentVoSelective.WhichPrice.valueOf(whichPrice.toUpperCase());
        InstrumentVoSelective instrumentVoSelective = InstrumentVoSelective.buildInstrumentVo(instrument, whichPriceEnum);
        HttpHeaders headers=new HttpHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        return new ResponseEntity<String>(new Gson().toJson(instrumentVoSelective),
                headers, HttpStatus.OK);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            HttpHeaders headers=new HttpHeaders();
            headers.add("Access-Control-Allow-Origin","*");
            return new ResponseEntity<String>("BAD SYMBOL",
                    headers, HttpStatus.BAD_REQUEST);
        }
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
