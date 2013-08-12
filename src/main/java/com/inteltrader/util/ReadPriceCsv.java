package com.inteltrader.util;

import com.inteltrader.entity.Price;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/16/13
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadPriceCsv {


    public static Price readPrice(String fileName, String instrName,
                                  Calendar date) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        // Double price = null;
        String str = "";
        String[] arrString;
        br.readLine();

        while ((str = br.readLine()) != null) {
            arrString = str.split(",");
            if (arrString[0].equals(instrName)) {
                br.close();
                return new Price(date, Double.parseDouble(arrString[5]),
                        Double.parseDouble(arrString[2]),
                        Double.parseDouble(arrString[4]),
                        Double.parseDouble(arrString[3]),
                        Double.parseDouble(arrString[7]),
                        Long.parseLong(arrString[8]));
            }

        }
        br.close();
        return null;
    }
}
