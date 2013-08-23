package com.inteltrader.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 5/7/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/test-Context.xml"})
public class DownloaderTest implements IConstants {
    @Autowired
    Global global;

    @Test
    public void downloadFileForGivenDate() {
        String url = "http://www.nseindia.com/content/historical/EQUITIES/2013/JAN/cm09JAN2013bhav.csv.zip";
        try {
            DownloadZip downloader = new DownloadZip(global.getProperties());
            downloader.downloadZip(url);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    @Test
    public void downloadAndExtractFileForGivenDate() {
        String url = "http://www.nseindia.com/content/historical/EQUITIES/2013/JAN/cm09JAN2013bhav.csv.zip";
        try {
            DownloadZip downloader = new DownloadZip(global.getProperties());
            ExtractZipFile extractZipFile = new ExtractZipFile(global.getProperties());
            downloader.downloadZip(url);
            extractZipFile.extractTemp();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
