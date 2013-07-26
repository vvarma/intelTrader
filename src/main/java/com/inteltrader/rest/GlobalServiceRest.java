package com.inteltrader.rest;

import com.google.gson.Gson;
import com.inteltrader.util.Global;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "/setTime/{today}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> setGlobalTime(@PathVariable("today") String today, HttpServletRequest request) {
        HttpHeaders headers=new HttpHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(today);
            Calendar cal = new GregorianCalendar();
            cal.setTime(date);
            System.out.println(date.toString());
            Global.setCalendar(cal);
            return new ResponseEntity<String>("yo",
                    headers, HttpStatus.OK);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return new ResponseEntity<String>("FORMAT DATE",
                    headers, HttpStatus.BAD_REQUEST);
        }


    }
    @RequestMapping(value = "/getTime", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> getGlobalTime( HttpServletRequest request) {
        HttpHeaders headers=new HttpHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        Calendar today=Global.getCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = sdf.format(today.getTime());
        Map<String,String> retMap=new HashMap<String, String>();
        retMap.put("today",todayString);
        return new ResponseEntity<String>(new Gson().toJson(retMap),
                headers, HttpStatus.OK);


    }
}