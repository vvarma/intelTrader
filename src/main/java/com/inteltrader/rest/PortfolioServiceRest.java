package com.inteltrader.rest;

import com.google.gson.Gson;
import com.inteltrader.entity.Portfolio;
import com.inteltrader.entity.view.PortfolioVo;
import com.inteltrader.service.InstrumentService;
import com.inteltrader.service.PortfolioService;
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

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/17/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Controller
@RequestMapping("/portfolio")
public class PortfolioServiceRest {
    @Autowired
    Global global;
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private InstrumentService instrumentService;

    @RequestMapping(value = "/create/{portfolioName}/{token}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> createPortfolio(@PathVariable("portfolioName") String portfolioName, @PathVariable("token") String token, HttpServletRequest request) {
        RestCodes responseCode = portfolioService.createPortfolio(portfolioName, token);
        return new ResponseEntity<String>(responseCode.toString(),
                new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/addInvestment/{portfolioName}/{symbolName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> addInvestment(@PathVariable("portfolioName") String portfolioName, @PathVariable("symbolName") String symbolName, HttpServletRequest request) throws NoSuchFieldException {
        RestCodes responseCode = null;
        responseCode = portfolioService.addToPortfolio(portfolioName, symbolName);
        return new ResponseEntity<String>(responseCode.toString(),
                new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/rollPortfolio/{portfolioName}/{rollDate}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> rollPortfolioTo(@PathVariable("portfolioName") String portfolioName, @PathVariable("rollDate") String rollDate, HttpServletRequest request) throws ParseException, OperationNotSupportedException, IOException, NoSuchFieldException {
        StringBuilder builder = new StringBuilder();
        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(rollDate);
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        if (cal.before(global.getCalendar())) {
            return new ResponseEntity<String>("Irretrievable Past",
                    new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        Calendar today = global.getCalendar();
        for (Calendar c = today; !c.after(cal); global.addCalendar()) {

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

    @RequestMapping(value = "/updatePortfolio/{portfolioName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> updatePortfolio(@PathVariable("portfolioName") String portfolioName, HttpServletRequest request) throws IOException, NoSuchFieldException {
        RestCodes responseCode = portfolioService.updatePortfolio(portfolioName);
        return new ResponseEntity<String>(responseCode.toString(),
                new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> listPortfolios(HttpServletRequest request) {
        List<String> portfolioStrin = portfolioService.listAllPortfolios();
        String[] p = new String[portfolioStrin.size()];
        int i = 0;
        for (String s : portfolioStrin) {
            p[i++] = s;
        }
        Map<String, String[]> portfolioMap = new HashMap<String, String[]>();
        portfolioMap.put("portfolio", p);
        return new ResponseEntity<String>(new Gson().toJson(portfolioMap),
                new HttpHeaders(), HttpStatus.OK);
    }


    @RequestMapping(value = "/load/{portfolioName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> loadInstrument(@PathVariable("portfolioName") String portfolioName, HttpServletRequest request) throws NoSuchFieldException {
        Portfolio portfolio = portfolioService.retrievePortfolio(portfolioName);
        PortfolioVo portfolioVo = new PortfolioVo(portfolio);
        return new ResponseEntity<String>(new Gson().toJson(portfolioVo),
                new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/pnl/{portfolioName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> getPortfolioPnL(@PathVariable("portfolioName") String portfolioName, HttpServletRequest request) throws NoSuchFieldException {
        Double pnl = portfolioService.calculatePnL(portfolioName);
        return new ResponseEntity<String>(new Gson().toJson(pnl),
                new HttpHeaders(), HttpStatus.OK);
    }
}
