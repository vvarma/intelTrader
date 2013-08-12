package com.inteltrader.rest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 8/1/13
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */

public class ResponseHeaderInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Allowing Cross Origin Access");
        response.setHeader("Access-Control-Allow-Origin","*");
        return true;
    }


}
