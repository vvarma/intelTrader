package com.inteltrader.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class ExtractZipFile {
    private Properties properties=new Properties();
    public ExtractZipFile() throws IOException{
        properties.load(new FileInputStream("intel.properties"));

    }

    public String extractTemp() throws IOException {
        String fName = properties.getProperty("TEMP_PATH");
        String entryName="";
        byte[] buf = new byte[1024];
        ZipInputStream zinstream = new ZipInputStream(
                new FileInputStream(fName));
        ZipEntry zentry = zinstream.getNextEntry();
        //Logger.info("Name of current Zip Entry : " + zentry + "\n");
        while (zentry != null) {
            entryName = zentry.getName();
            //Logger.info("Name of  Zip Entry : " + entryName);
            FileOutputStream outstream = new FileOutputStream(properties.getProperty("DATA_PATH")+entryName);
            int n;

            while ((n = zinstream.read(buf, 0, 1024)) > -1) {
                outstream.write(buf, 0, n);

            }
           // Logger.info("Successfully Extracted File Name : "                  + entryName);
            outstream.close();

            zinstream.closeEntry();
            zentry = zinstream.getNextEntry();
        }
        zinstream.close();
        return entryName;


    }
}
