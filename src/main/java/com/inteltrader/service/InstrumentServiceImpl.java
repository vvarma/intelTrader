package com.inteltrader.service;

import com.inteltrader.dao.IInstrumentDao;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Investment;
import com.inteltrader.entity.Portfolio;
import com.inteltrader.entity.Price;
import com.inteltrader.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Transactional(propagation = Propagation.REQUIRED)
public class InstrumentServiceImpl implements InstrumentService {
    @Autowired
    Global global;
    @Autowired
    private IInstrumentDao instrumentDao;
    private Properties properties;
    @Autowired
    private PortfolioService portfolioService;
    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public Instrument retrieveInstrument(String symbolName) throws NoSuchFieldException {
        logger.trace("Retrieve Instruments..");
        Instrument instrument = null;
        try {
            instrument = instrumentDao.retrieveInstrument(symbolName);
        } catch (NoSuchFieldException e) {
            if (instrument == null) {
                logger.trace("Instrument does not exist in database.. Creating Instrument.");
                Calendar startDate = (GregorianCalendar) global.getCalendar().clone();
                startDate.add(Calendar.YEAR, -5);
                if (createInstrument(symbolName, startDate) == RestCodes.SUCCESS) {
                    instrument = instrumentDao.retrieveInstrument(symbolName);
                }
            }
        }



        return instrument;
    }


    @Override
    public RestCodes updateInstruments(String symbolName) throws NoSuchFieldException {
        logger.debug("Updating Instruments..");

        Instrument instrument = null;
        instrument = retrieveInstrument(symbolName);
        Calendar startDate = (Calendar) instrument.getCurrentPrice().getTimeStamp().clone();
        Calendar endDate = global.getCalendar();
        //from start to end start excluded end included
        Instrument temp = getSingleInstrumentGivenDateAndName(startDate, endDate, symbolName);
        if (temp.getPriceList().size() > 0) {
            instrument.getPriceList().addAll(temp.getPriceList());
            logger.trace("Writing instrument to Dao");
            instrumentDao.updateInstrument(instrument);
            logger.trace("Success.");
        } else {
            return RestCodes.NO_COMMENT;
        }


        return RestCodes.SUCCESS;
    }

    @Override
    public List<Price> getNewPrices(String symbolName, Price currentPrice) throws NoSuchFieldException {

        Instrument instrument = retrieveInstrument(symbolName);
        List<Price> priceList = instrument.getPriceList();
        int index = priceList.indexOf(currentPrice);
        int maxIndex = priceList.size() - 1;
        if (index >= maxIndex)
            return new ArrayList<Price>();
        return new ArrayList<Price>(priceList.subList(index + 1, maxIndex + 1));
    }

    @Override
    public RestCodes createInstrument(String symbolName, Calendar startDate) throws NoSuchFieldException {
        Calendar endDate = global.getCalendar();
        Instrument instrument = getSingleInstrumentGivenDateAndName(startDate, endDate, symbolName);
        instrumentDao.createInstrument(instrument);
        return RestCodes.SUCCESS;
    }

    private Instrument getSingleInstrumentGivenDateAndName(
            Calendar startDate, Calendar endDate, String symbol) throws NoSuchFieldException {
        Instrument instrument = new Instrument(symbol);
        String path = System.getProperty("java.io.tmpdir")+"/";
        do{
            startDate.add(Calendar.DATE,1);
            String fileName = path;
            if (isWeekDay(startDate)) {
                try {
                    String genFileName = createFilenamGivenDate(startDate);
                    File file = new File(fileName + genFileName);
                    if (file.exists()) {
                        logger.debug("file exists" + file.getPath()+'\n');
                        fileName = fileName + genFileName;
                    } else {
                        logger.debug("file does not exist" +file.getPath() +"...downloading\n");
                        fileName = fileName
                                + createUrlDownloadAndExtractFileGivenDate(startDate);
                    }
                    Price instrPrice = ReadPriceCsv.readPrice(fileName, symbol, (Calendar) startDate.clone());
                    if (instrPrice == null) {
                        throw new NoSuchFieldException(symbol + " Not a valid symbol.");
                    }
                    instrument.getPriceList().add(instrPrice);
                } catch (IOException e) {
                    logger.debug("Public Holiday" + startDate.getTime());
                }

            }

        }while (startDate.before(endDate)) ;
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
        DownloadZip downloadZip = new DownloadZip(global.getProperties());
        ExtractZipFile extractZip = new ExtractZipFile(global.getProperties());
        url = createUrlGivenDate(i);
        downloadZip.downloadZip(url);
        return extractZip.extractTemp();
    }

    public InstrumentServiceImpl() {
    }

    public IInstrumentDao getInstrumentDao() {
        return instrumentDao;
    }

    public void setInstrumentDao(IInstrumentDao instrumentDao) {
        this.instrumentDao = instrumentDao;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public PortfolioService getPortfolioService() {
        return portfolioService;
    }

    public void setPortfolioService(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }
}
