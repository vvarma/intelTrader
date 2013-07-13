package com.inteltrader.util;

import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 5/7/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class DownloaderTest {

    @Test
    public void downloadFileForGivenDate(){
        String url="http://www.nseindia.com/content/historical/EQUITIES/2013/JAN/cm09JAN2013bhav.csv.zip";
        try {
            DownloadZip downloader=new DownloadZip();
            downloader.downloadZip(url);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
