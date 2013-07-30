package com.inteltrader.util;

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
    private Calendar calendar;
    private Properties properties;

    public Global() throws IOException {
        calendar=new GregorianCalendar();
        calendar.set(2013,00,01);
        properties=new Properties();
        properties.load(getClass().getResourceAsStream("/intel.properties"));
    }

    public Calendar getCalendar() {
        return (Calendar)calendar;
    }

    public void setCalendar(Calendar calendar2) {
        calendar = calendar2;
    }
    public void updateCalendar(){
        calendar=new GregorianCalendar();
    }
    public void addCalendar(){
        calendar.add(Calendar.DATE,1);
    }

    public Properties getProperties()  {
       return properties;
    }
}
