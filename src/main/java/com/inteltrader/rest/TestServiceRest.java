package com.inteltrader.rest;

import com.inteltrader.service.InstrumentService;
import com.inteltrader.service.PortfolioService;
import com.inteltrader.util.Global;
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
 * Date: 5/3/13
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/test")
public class TestServiceRest {
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private PortfolioService portfolioService;

    @RequestMapping(value = "/global/setTime/{dd}/{mm}/{yyyy}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> createPortfolioTest(@PathVariable("dd") String dd,@PathVariable("mm") String mm,@PathVariable("yyyy") String yyyy, HttpServletRequest request) {
        Calendar cal=new GregorianCalendar();
        cal.set(Calendar.YEAR, Integer.parseInt(yyyy));
        cal.set(Calendar.MONTH, Integer.parseInt(mm));
        cal.set(Calendar.DATE, Integer.parseInt(dd));
        Global.setCalendar(cal);
        return new ResponseEntity<String>("yo",
                new HttpHeaders(), HttpStatus.OK);

    }
    @RequestMapping(value = "/portfolio/create/{portfolioName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> createPortfolioTest(@PathVariable("portfolioName") String portfolioName, HttpServletRequest request) {
        portfolioService.createPortfolio(portfolioName);
        return new ResponseEntity<String>("yo",
                new HttpHeaders(), HttpStatus.OK);

    }
    @RequestMapping(value = "/portfolio/addToPortfolio/{portfolioName}/{symbolName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> addToPortfolioTest(@PathVariable("portfolioName") String portfolioName,@PathVariable("symbolName") String symbolName, HttpServletRequest request) {
        portfolioService.addToPortfolio(portfolioName, symbolName);
        return new ResponseEntity<String>("yo",
                new HttpHeaders(), HttpStatus.OK);

    }
    @RequestMapping(value = "/portfolio/updatePortfolio/{portfolioName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> updatePortfolioTest(@PathVariable("portfolioName") String portfolioName, HttpServletRequest request) {
        StringBuilder builder=new StringBuilder();
        Calendar today=new GregorianCalendar();
        Calendar fake=Global.getCalendar();
        for (Calendar c=fake;c.before(today);Global.addCalendar()) {
            portfolioService.updatePortfolio(portfolioName);
            builder.append(portfolioService.calculatePnL(portfolioName));
            builder.append(" "+Global.getCalendar().get(Calendar.DATE)+" "+Global.getCalendar().get(Calendar.MONTH)+" "+Global.getCalendar().get(Calendar.YEAR));
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
