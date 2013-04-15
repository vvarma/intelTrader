package com.inteltrader.service;

import com.inteltrader.dao.IInstrumentDao;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;
import com.inteltrader.util.DownloadZip;
import com.inteltrader.util.ExtractZipFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class InstrumentServiceImpl implements InstrumentService {
    private IInstrumentDao instrumentDao;
    private Properties properties=new Properties();

    @Override
    public Instrument retrieveInstrument(String symbolName) {
        Instrument instrument=instrumentDao.retrieveInstrument(symbolName);
        if(instrument==null){
            Calendar startDate=new GregorianCalendar();
            startDate.add(Calendar.YEAR,-2);

        }
         return instrument;
    }

    @Override
    public Instrument retrieveInstrument(Long instrumentId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateInstruments() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteInstrument(Instrument instrument) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void createInstrument(String symbolName, Calendar startDate) {

        Calendar endDate=startDate;
        endDate.add(Calendar.YEAR,-2);
        try{
            Instrument instrument=getSingleInstrumentGivenDateAndName(startDate,endDate,symbolName);
            instrumentDao.createInstrument(instrument);
        }   catch (IOException e){
            e.printStackTrace();
            e.printStackTrace();
        }


    }

    private Instrument getSingleInstrumentGivenDateAndName(
            Calendar startDate, Calendar endDate, String symbol) throws IOException{
       Instrument instrument=new Instrument(symbol);
        for (Calendar i = startDate; i.before(endDate); i.add(Calendar.DATE, 1)) {
            String fileName = properties.getProperty("DATA_PATH");

            if (isWeekDay(i)) {
                String genFileName = createFilenamGivenDate(i);
                File file = new File(fileName + genFileName);
                if (file.exists()) {
                   // Logger.trace(genFileName + " exists!");
                    fileName = fileName + genFileName;
                } else {
                  //  Logger.trace("downloader called.. ");
                    fileName = fileName
                            + createUrlDownloadAndExtractFileGivenDate(i);
                }


                Price instrPrice = new Price(ReadPriceCsv.readPrice(
                        fileName, symbol), i.getTime());
                instrument.getPriceList().add(instrPrice);
            }

        }

        return instrument;
    }
    private static String createFilenamGivenDate(Calendar i) {
        StringBuilder fileNameBuilder = new StringBuilder("cm");
        if (i.get(Calendar.DATE) < 10) {
            fileNameBuilder.append("0");
        }
        fileNameBuilder.append(i.get(Calendar.DATE));
        fileNameBuilder.append(i.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US).toUpperCase());
        // urlBuilder.append("20");
        fileNameBuilder.append(i.get(Calendar.YEAR));
        fileNameBuilder.append("bhav.csv");
        return fileNameBuilder.toString();
    }
    private boolean isWeekDay(Calendar i) {

        if (i.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                || i.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            return false;
        return true;
    }

    private String createUrlGivenDate(Calendar i) {

		/*
		 * String url=
		 * "http://www.nseindia.com/content/historical/EQUITIES/2013/JAN/cm09JAN2013bhav.csv.zip"
		 * ;
		 */
        StringBuilder urlBuilder = new StringBuilder(
                "http://www.nseindia.com/content/historical/EQUITIES/");
        urlBuilder.append(i.get(Calendar.YEAR));
        urlBuilder.append("/");
        urlBuilder.append(i.getDisplayName(Calendar.MONTH, Calendar.SHORT,
                Locale.US).toUpperCase());
        urlBuilder.append("/cm");
        if (i.get(Calendar.DATE) < 10) {
            urlBuilder.append("0");
        }
        urlBuilder.append(i.get(Calendar.DATE));
        urlBuilder.append(i.getDisplayName(Calendar.MONTH, Calendar.SHORT,
                Locale.US).toUpperCase());
        urlBuilder.append(i.get(Calendar.YEAR));
        urlBuilder.append("bhav.csv.zip");
        //Logger.trace(urlBuilder.toString());
        return urlBuilder.toString();
    }
    private String createUrlDownloadAndExtractFileGivenDate(Calendar i)
            throws IOException {
        String url;
        DownloadZip downloadZip = new DownloadZip();
        ExtractZipFile extractZip = new ExtractZipFile();
        url = createUrlGivenDate(i);
        downloadZip.downloadZip(url);
        return extractZip.extractTemp();
    }

    public InstrumentServiceImpl() throws IOException{
        properties.load(new FileInputStream("intel.properties"));
    }
}
