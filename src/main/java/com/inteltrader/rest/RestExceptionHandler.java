package com.inteltrader.rest;

import com.google.gson.Gson;
import org.aspectj.weaver.patterns.ParserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 8/2/13
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public @ResponseBody ResponseEntity<String> handleIOExceptions(IOException e){
        Map<String,String> errorMap=new HashMap<String, String>();
        errorMap.put("message","Oops! Something went wrong.");
        return new ResponseEntity<String>(new Gson().toJson(errorMap),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public @ResponseBody ResponseEntity<String> handleParseExceptions(ParserException e){
        Map<String,String> errorMap=new HashMap<String, String>();
        errorMap.put("message","Data Format Error");
        return new ResponseEntity<String>(new Gson().toJson(errorMap),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public @ResponseBody ResponseEntity<String> handleOpNotSupportedExceptions(OperationNotSupportedException e){
        Map<String,String> errorMap=new HashMap<String, String>();
        errorMap.put("message",e.getMessage());
        return new ResponseEntity<String>(new Gson().toJson(errorMap),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public @ResponseBody ResponseEntity<String> handleNoSuchFieldExceptions(NoSuchFieldException e){
        Map<String,String> errorMap=new HashMap<String, String>();
        errorMap.put("message",e.getMessage());
        return new ResponseEntity<String>(new Gson().toJson(errorMap),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


}
