package com.inteltrader.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 8/28/13
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args){
        Calendar strt=new GregorianCalendar(2013,00,01);
        Calendar end=new GregorianCalendar(2013,00,16);
        System.out.println("started on" +strt.getTime());
        System.out.println("ended on" +end.getTime());
        while(strt.before(end)){
            strt.add(Calendar.DATE,1);

            System.out.println("today" + strt.getTime());
        }

    }

}
