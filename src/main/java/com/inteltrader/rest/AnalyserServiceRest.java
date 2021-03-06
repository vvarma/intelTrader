package com.inteltrader.rest;

import com.google.gson.Gson;
import com.inteltrader.advisor.InstrumentWrapper;
import com.inteltrader.entity.States;
import com.inteltrader.entity.view.InstrumentVo;
import com.inteltrader.entity.view.StatesVo;
import com.inteltrader.service.AnalyserService;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/27/13
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Controller
@RequestMapping("/analyser")
public class AnalyserServiceRest {
    @Autowired
    private AnalyserService analyserService;

    @RequestMapping(value = "/load/{symbol}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> loadInstrument(@PathVariable("symbol") String symbolName, HttpServletRequest request) throws NoSuchFieldException, IOException {
        String[] tokens = {"MACD", "RSI", "BBAND"};
        InstrumentWrapper taWrapper = analyserService.getWrapper(symbolName, tokens);
        InstrumentVo instrumentVo = new InstrumentVo(taWrapper);
        return new ResponseEntity<String>(new Gson().toJson(instrumentVo),
                new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/legalIndicators", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> getLegalIndicators(HttpServletRequest request) throws NoSuchFieldException {
        String indicatorArr[] = {"MACD", "BBAND", "RSI"};
        Map<String, String[]> indicatorMap = new HashMap<String, String[]>();
        indicatorMap.put("indicators", indicatorArr);
        return new ResponseEntity<String>(new Gson().toJson(indicatorMap),
                new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/states/{statesName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> getStates(@PathVariable("statesName") String statesName, HttpServletRequest request) throws NoSuchFieldException {
        States states = analyserService.getStates(statesName);
        StatesVo statesVo = new StatesVo(states);
        return new ResponseEntity<String>(new Gson().toJson(statesVo),
                new HttpHeaders(), HttpStatus.OK);
    }
}
