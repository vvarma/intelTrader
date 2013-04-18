package com.inteltrader.rest;

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
import java.util.Calendar;
import java.util.GregorianCalendar;

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
}
