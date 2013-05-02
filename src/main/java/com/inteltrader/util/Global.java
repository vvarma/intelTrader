package com.inteltrader.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/2/13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Global {
    private static Calendar calendar=new GregorianCalendar();

    public static Calendar getCalendar() {
        return calendar;
    }

    public static void setCalendar(Calendar calendar2) {
        calendar = calendar2;
    }
    public static void updateCalendar(){
        calendar=new GregorianCalendar();
    }
    public static void addCalendar(){
        calendar.add(Calendar.DATE,1);
    }
}
