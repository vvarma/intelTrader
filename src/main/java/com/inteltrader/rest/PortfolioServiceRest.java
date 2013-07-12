package com.inteltrader.rest;

import com.google.gson.Gson;
import com.inteltrader.entity.Portfolio;
import com.inteltrader.entity.view.PortfolioVo;
import com.inteltrader.service.InstrumentService;
import com.inteltrader.service.PortfolioService;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/17/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/portfolio")
public class PortfolioServiceRest {
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private InstrumentService instrumentService;
    @RequestMapping(value = "/create/{portfolioName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> createPortfolio(@PathVariable("portfolioName") String portfolioName, HttpServletRequest request) {

        RestCodes responseCode = portfolioService.createPortfolio(portfolioName);

        return new ResponseEntity<String>(responseCode.toString(),
                new HttpHeaders(), HttpStatus.OK);

    }
    @RequestMapping(value = "/addInvestment/{portfolioName}/{symbolName}", method = RequestMethod.GET)
     public
     @ResponseBody
     ResponseEntity<String> addInvestment(@PathVariable("portfolioName") String portfolioName,@PathVariable("symbolName") String symbolName, HttpServletRequest request) {

        RestCodes responseCode = portfolioService.addToPortfolio(portfolioName,symbolName);

        return new ResponseEntity<String>(responseCode.toString(),
                new HttpHeaders(), HttpStatus.OK);

    }
    @RequestMapping(value = "/updatePortfolio/{portfolioName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> updatePortfolio(@PathVariable("portfolioName") String portfolioName, HttpServletRequest request) {
        if (instrumentService.updateInstruments(portfolioName)!=RestCodes.NO_COMMENT){
            try {
                RestCodes responseCode = portfolioService.updatePortfolio(portfolioName);

                return new ResponseEntity<String>(responseCode.toString(),
                        new HttpHeaders(), HttpStatus.OK);
            }  catch (IOException e){
                return new ResponseEntity<String>(e.toString(),
                        new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)    ;
            }
        }else{
            return new ResponseEntity<String>("Nothing to Update",
                    new HttpHeaders(), HttpStatus.OK);
        }

    }
    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
     public
    @ResponseBody
    ResponseEntity<String> listPortfolios(HttpServletRequest request){
        List<String> portfolioStrin=portfolioService.listAllPortfolios();
        String[] p=new String[portfolioStrin.size()];
        int i=0;
        for (String s:portfolioStrin){
           p[i++]=s;
        }

        Map<String,String[]> portfolioMap=new HashMap<String, String[]>();
        portfolioMap.put("portfolio",p);
        HttpHeaders headers=new HttpHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        return new ResponseEntity<String>(new Gson().toJson(portfolioMap),
                headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/load/{portfolioName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> loadInstrument(@PathVariable("portfolioName") String portfolioName, HttpServletRequest request){
        Portfolio portfolio= portfolioService.retrievePortfolio(portfolioName);
        PortfolioVo portfolioVo=new PortfolioVo(portfolio);
        HttpHeaders headers=new HttpHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        return new ResponseEntity<String>(new Gson().toJson(portfolioVo),
                headers, HttpStatus.OK);
    }
    @RequestMapping(value = "/pnl/{portfolioName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> getPortfolioPnL(@PathVariable("portfolioName") String portfolioName, HttpServletRequest request){
        Double pnl= portfolioService.calculatePnL(portfolioName);

        return new ResponseEntity<String>(new Gson().toJson(pnl),
                new HttpHeaders(), HttpStatus.OK);
    }
}
