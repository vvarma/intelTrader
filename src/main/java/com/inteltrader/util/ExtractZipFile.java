package com.inteltrader.util;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/15/13
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExtractZipFile  {
    Logger logger=Logger.getLogger(getClass());
    Properties properties;
    public ExtractZipFile(Properties properties) {
        this.properties=properties;
    }

    public String extractTemp() throws IOException {

        String fName = System.getProperty("java.io.tmpdir")+properties.getProperty("TEMP_PATH");
        String entryName = "";
        byte[] buf = new byte[1024];
        ZipInputStream zinstream = new ZipInputStream(
                new FileInputStream(fName));
        ZipEntry zentry = zinstream.getNextEntry();
        String path = System.getProperty("java.io.tmpdir");
        logger.debug(path);
        while (zentry != null) {
            entryName = zentry.getName();
            FileOutputStream outstream = new FileOutputStream(path +'/'+ entryName);
            int n;

            while ((n = zinstream.read(buf, 0, 1024)) > -1) {
                outstream.write(buf, 0, n);

            }
            outstream.close();

            zinstream.closeEntry();
            zentry = zinstream.getNextEntry();
        }
        zinstream.close();
        return entryName;


    }
}
