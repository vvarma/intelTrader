package com.inteltrader.rest;

import com.google.gson.Gson;
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

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 7/26/13
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/global")
public class GlobalServiceRest {
    @Autowired
    Global global;
    @Autowired
    PortfolioService portfolioService;

    @RequestMapping(value = "/setTime/{today}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> setGlobalTime(@PathVariable("today") String today, HttpServletRequest request) throws ParseException, OperationNotSupportedException {
        Calendar cal = parseToday(today);
        global.setCalendar(cal);
        return new ResponseEntity<String>("yo",
                new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/addTime", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> incrementDate(HttpServletRequest request) throws OperationNotSupportedException {
        global.addCalendar();
        return new ResponseEntity<String>("yo",
                new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getTime", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> getGlobalTime(HttpServletRequest request) {
        Calendar today = global.getCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = sdf.format(today.getTime());
        Map<String, String> retMap = new HashMap<String, String>();
        retMap.put("today", todayString);
        return new ResponseEntity<String>(new Gson().toJson(retMap),
                new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/roll/{rollDate}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> rollTodayTo(@PathVariable("rollDate") String rollDate, HttpServletRequest request) throws ParseException, OperationNotSupportedException, NoSuchFieldException, IOException, CloneNotSupportedException {
        StringBuilder builder = new StringBuilder();
        Calendar cal = parseToday(rollDate);
        while(global.getCalendar().before(cal)||global.getCalendar().equals(cal)){
            global.addCalendar();
            Calendar c=global.getCalendar();
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                    || c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                continue;
            }
            portfolioService.updateAllPortfolio();
        }
        return new ResponseEntity<String>(builder.toString(),
                new HttpHeaders(), HttpStatus.OK);
    }

    Calendar parseToday(String todayString) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(todayString);
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal;
    }
}
