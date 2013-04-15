package com.inteltrader.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/15/13
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */

public class DownloadZip {
    Properties properties=new Properties();
    public DownloadZip() throws IOException{
        properties.load(new FileInputStream("intel.properties"));
    }

    public void downloadZip(String urlFormed) {
        try
        {
	        /*
	         * Get a connection to the URL and start up
	         * a buffered reader.
	         */
            long startTime = System.currentTimeMillis();

            //Logger.info("Connecting to NSE...\n");

            URL url = new URL(urlFormed);
            url.openConnection();
            InputStream reader = url.openStream();

	        /*
	         * Setup a buffered file writer to write
	         * out what we read from the website.
	         */
            FileOutputStream writer = new FileOutputStream(properties.getProperty("TEMP_PATH"));
            byte[] buffer = new byte[153600];
            int totalBytesRead = 0;
            int bytesRead = 0;

           // Logger.info("Reading ZIP file 150KB blocks at a time.\n");

            while ((bytesRead = reader.read(buffer)) > 0)
            {
                writer.write(buffer, 0, bytesRead);
                buffer = new byte[153600];
                totalBytesRead += bytesRead;
            }

            long endTime = System.currentTimeMillis();

            //Logger.info("Done. " + (new Integer(totalBytesRead).toString()) + " bytes read (" + (new Long(endTime - startTime).toString()) + " millseconds).\n");
            writer.close();
            reader.close();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

}
