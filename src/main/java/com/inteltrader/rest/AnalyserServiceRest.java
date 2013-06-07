package com.inteltrader.rest;

import com.google.gson.Gson;
import com.inteltrader.advisor.tawrapper.InstrumentWrapper;
import com.inteltrader.advisor.tawrapper.TAWrapper;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.view.InstrumentVo;
import com.inteltrader.service.AnalyserService;
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
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/27/13
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/analyser")
public class AnalyserServiceRest {
    @Autowired
    private AnalyserService analyserService;
    @RequestMapping(value = "/load/{symbol}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> loadInstrument(@PathVariable("symbol") String symbolName, HttpServletRequest request){
        try{
            InstrumentWrapper taWrapper=analyserService.getWrapper(symbolName);
            InstrumentVo instrumentVo=new InstrumentVo(taWrapper);
            HttpHeaders headers=new HttpHeaders();
            headers.add("Access-Control-Allow-Origin","*");
            return new ResponseEntity<String>(new Gson().toJson(instrumentVo),
                    headers, HttpStatus.OK);
        } catch (IOException exception){
            return new ResponseEntity<String>("error io",
                    new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
