package com.inteltrader.util;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 7/30/13
 * Time: 1:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Aivehi implements IConstants {
    Global global;

    public Aivehi() throws IOException {
        global = new Global();
    }

    public String getFuckinPath(){

        String url="http://www.nseindia.com/content/historical/EQUITIES/2013/JAN/cm09JAN2013bhav.csv.zip";
        try {
            DownloadZip downloader=new DownloadZip(global.getProperties());
            ExtractZipFile extractZipFile=new ExtractZipFile(global.getProperties());
            downloader.downloadZip(url);
            extractZipFile.extractTemp();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return"blah";
    }
    public String getFuckinPathData(){
        InputStream ip= getClass().getResourceAsStream("/intel.properties");
        Properties properties=new Properties();
        try {
            properties.load(ip);
            String s=properties.getProperty("DATA_PATH")+"\\abc.txt" ;
            BufferedWriter wr=new BufferedWriter(new FileWriter(s));
            wr.write("fuck this shit yo123");
            wr.flush();
            wr.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "bla blas";
    }
}
