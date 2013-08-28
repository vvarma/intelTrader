package com.inteltrader.util;

import javax.naming.OperationNotSupportedException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 5/2/13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Global {
    private final Calendar calendar;
    private Properties properties;

    public Global() throws IOException {
        calendar = new GregorianCalendar();
        calendar.set(2013, 00, 01,00,00,00);
        properties = new Properties();
        properties.load(getClass().getResourceAsStream("/intel.properties"));
    }

    public Calendar getCalendar() {
        return (Calendar) calendar.clone();
    }

    public void setCalendar(Calendar calendar2) throws OperationNotSupportedException {
        if (beforeDate(calendar2,calendar) || afterDate(calendar2,new GregorianCalendar())) {
            throw new OperationNotSupportedException("Not a valid date.");
        }
        calendar.setTime(calendar2.getTime());
    }

    public void addCalendar() throws OperationNotSupportedException {
        if (calendar.equals(new GregorianCalendar())) {
            throw new OperationNotSupportedException("Not a valid operation");
        }
        calendar.add(Calendar.DATE, 1);
    }

    public Properties getProperties() {
        return properties;
    }
    public static boolean beforeDate(Calendar a, Calendar b) {
        if (a.get(Calendar.YEAR) < b.get(Calendar.YEAR)) {
            return true;
        } else if (a.get(Calendar.MONTH) < b.get(Calendar.MONTH)) {
            return true;
        } else if (a.get(Calendar.DATE) < b.get(Calendar.DATE)) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean afterDate(Calendar a, Calendar b) {
        if (a.get(Calendar.YEAR) > b.get(Calendar.YEAR)) {
            return true;
        } else if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)) {
            return true;
        } else if (a.get(Calendar.DATE) > b.get(Calendar.DATE)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean setProperties(String brokerage, Double brokerage1) {
        if (brokerage.equalsIgnoreCase("brokerage")){
            if (brokerage1<0){
                return false;
            }
            properties.setProperty("BROKERAGE",brokerage1.toString());

        }
        return true;
    }
}
