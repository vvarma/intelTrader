package com.inteltrader.rest;

import com.inteltrader.service.InstrumentService;
import com.inteltrader.service.PortfolioService;
import com.inteltrader.util.Aivehi;
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

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/3/13
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/test")
public class TestServiceRest {
    @Autowired
    Global global;
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private PortfolioService portfolioService;

    @RequestMapping(value = "/global/setTime/{dd}/{mm}/{yyyy}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> createPortfolioTest(@PathVariable("dd") String dd, @PathVariable("mm") String mm, @PathVariable("yyyy") String yyyy, HttpServletRequest request) throws OperationNotSupportedException {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, Integer.parseInt(yyyy));
        cal.set(Calendar.MONTH, Integer.parseInt(mm));
        cal.set(Calendar.DATE, Integer.parseInt(dd));
        global.setCalendar(cal);
        return new ResponseEntity<String>("yo",
                new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/portfolio/create/{portfolioName}/{token}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> createPortfolioTest(@PathVariable("portfolioName") String portfolioName, @PathVariable("token") String token, HttpServletRequest request) {
        portfolioService.createPortfolio(portfolioName, token);
        return new ResponseEntity<String>("yo",
                new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/portfolio/addToPortfolio/{portfolioName}/{symbolName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> addToPortfolioTest(@PathVariable("portfolioName") String portfolioName, @PathVariable("symbolName") String symbolName, HttpServletRequest request) throws NoSuchFieldException {
        portfolioService.addToPortfolio(portfolioName, symbolName);
        return new ResponseEntity<String>("yo",
                new HttpHeaders(), HttpStatus.OK);
    }


    @RequestMapping(value = "/portfolio/updatePortfolio/{portfolioName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> updatePortfolioTest(@PathVariable("portfolioName") String portfolioName, HttpServletRequest request) throws NoSuchFieldException, OperationNotSupportedException, IOException {
        StringBuilder builder = new StringBuilder();
        Calendar today = new GregorianCalendar();
        Calendar fake = global.getCalendar();
        for (Calendar c = fake; c.before(today); global.addCalendar()) {
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                    || c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                continue;
            portfolioService.updatePortfolio(portfolioName);
            builder.append(portfolioService.calculatePnL(portfolioName));
            builder.append(" " + global.getCalendar().get(Calendar.DATE) + " " + global.getCalendar().get(Calendar.MONTH) + " " + global.getCalendar().get(Calendar.YEAR));
            builder.append('\n');
        }
        return new ResponseEntity<String>(builder.toString(),
                new HttpHeaders(), HttpStatus.OK);
    }

    public InstrumentService getInstrumentService() {
        return instrumentService;
    }

    public void setInstrumentService(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    public PortfolioService getPortfolioService() {
        return portfolioService;
    }

    public void setPortfolioService(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }
}
