package com.inteltrader.util;



import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/15/13
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */

public class DownloadZip  {
    Logger logger= Logger.getLogger(this.getClass());
    Properties properties;

    public DownloadZip(Properties properties) {
        this.properties=properties;
    }

    public void downloadZip(String urlFormed) throws IOException {

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
        String path=properties.getProperty("TEMP_PATH");
        FileOutputStream writer = new FileOutputStream(path);
        byte[] buffer = new byte[153600];
        int totalBytesRead = 0;
        int bytesRead = 0;
        logger.debug("Reading ZIP file 150KB blocks at a time.\n");
        logger.debug(path);
        // Logger.info("Reading ZIP file 150KB blocks at a time.\n");

        while ((bytesRead = reader.read(buffer)) > 0) {
            writer.write(buffer, 0, bytesRead);
            buffer = new byte[153600];
            totalBytesRead += bytesRead;
        }

        long endTime = System.currentTimeMillis();

        logger.info("Done. " + (new Integer(totalBytesRead).toString()) + " bytes read (" + (new Long(endTime - startTime).toString()) + " millseconds).\n");
        writer.close();
        reader.close();


    }

}
