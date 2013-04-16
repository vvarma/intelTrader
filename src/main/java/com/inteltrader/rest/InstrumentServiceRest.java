package com.inteltrader.rest;

import com.inteltrader.service.InstrumentService;
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
 * Date: 4/16/13
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/instrument")
public class InstrumentServiceRest {
    @Autowired
    private InstrumentService instrumentService;

    @RequestMapping(value = "/create/{symbol}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> checkExistUser(@PathVariable("symbol") String symbolName, HttpServletRequest request) {

        Calendar strtDate=new GregorianCalendar();
        strtDate.add(Calendar.YEAR,-2);
        RestCodes responseCode = instrumentService.createInstrument(symbolName,strtDate);

        return new ResponseEntity<String>(responseCode.toString(),
                new HttpHeaders(), HttpStatus.OK);

    }
}
