package com.inteltrader.service;

import com.inteltrader.dao.IInstrumentDao;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Investment;
import com.inteltrader.entity.Portfolio;
import com.inteltrader.entity.Price;
import com.inteltrader.util.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 4/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class InstrumentServiceImpl implements InstrumentService {
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Autowired
    private IInstrumentDao instrumentDao;
    private Properties properties=new Properties();
    @Autowired
    private PortfolioService portfolioService;

    @Override
    public Instrument retrieveInstrument(String symbolName) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        Instrument instrument=instrumentDao.retrieveInstrument(entityManager,symbolName);
        if(instrument==null){
            Calendar startDate=(GregorianCalendar) Global.getCalendar().clone();
            startDate.add(Calendar.YEAR,-2);
            if(createInstrument(symbolName,startDate)==RestCodes.SUCCESS){
                instrument=instrumentDao.retrieveInstrument(entityManager,symbolName);
            }

        }
         return instrument;
    }



    @Override
    public RestCodes updateInstruments(String portfolioName){
        List<String> symbolNameList=new ArrayList<String>();
        Portfolio portfolio=portfolioService.retrievePortfolio(portfolioName);
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        try{
            for(Investment investment:portfolio.getInvestmentList()){
               symbolNameList.add(investment.getSymbolName());
            }
            System.out.println(symbolNameList);
            for(String symbolName:symbolNameList){
                Instrument instrument=instrumentDao.retrieveInstrument(entityManager,symbolName);
                Calendar startDate=instrument.getCurrentPrice().getTimeStamp();
                Calendar endDate=(GregorianCalendar)Global.getCalendar().clone();
                for (Calendar calendar=startDate;calendar.before(endDate);calendar.add(Calendar.DATE,1)){
                    String fileName = properties.getProperty("DATA_PATH");
                    System.out.println("in for loop");
                    if (isWeekDay(calendar)) {
                        String genFileName = createFilenamGivenDate(calendar);
                        File file = new File(fileName + genFileName);
                        try{
                            if (file.exists()) {
                                System.out.println(genFileName + " exists!");
                                fileName = fileName + genFileName;
                            } else {
                                System.out.println("downloader called.. ");
                                fileName = fileName
                                        + createUrlDownloadAndExtractFileGivenDate(calendar);
                            }
                            Price instrPrice = ReadPriceCsv.readPrice(fileName, symbolName, (Calendar)calendar.clone());
                            instrument.getPriceList().add(instrPrice);
                        }catch (FileNotFoundException e){
                            System.err.println("Public Holiday" + calendar.getTime());
                        }

                    }
                }
                entityManager.getTransaction().begin();
                try {
                    instrumentDao.updateInstrument(entityManager,instrument);
                }catch (RuntimeException e){
                    e.printStackTrace();
                    return RestCodes.FAILURE;
                }  finally {
                    entityManager.getTransaction().commit();
                }
            }
            return RestCodes.SUCCESS;
        }catch (IOException e){
            e.printStackTrace();
            return RestCodes.FAILURE;
        }
    }

    @Override
    public RestCodes createInstrument(String symbolName, Calendar startDate) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Calendar endDate=(Calendar)startDate.clone();
        endDate.add(Calendar.YEAR,2);
        endDate.add(Calendar.MONTH,-2);
       // endDate.roll(Calendar.YEAR,2);
        System.out.println(startDate);
        System.out.println(endDate);
        try{
            Instrument instrument=getSingleInstrumentGivenDateAndName(startDate,endDate,symbolName);
            System.out.println(instrument);
            instrumentDao.createInstrument(entityManager,instrument);
            return RestCodes.SUCCESS;
        }  catch (IOException e){
            e.printStackTrace();
            e.printStackTrace();
            return RestCodes.FAILURE;
        }catch (RuntimeException e){
            e.printStackTrace();
            e.printStackTrace();
            return RestCodes.FAILURE;
        }finally {
            entityManager.getTransaction().commit();
        }


    }

    private Instrument getSingleInstrumentGivenDateAndName(
            Calendar startDate, Calendar endDate, String symbol) throws IOException{
       Instrument instrument=new Instrument(symbol);
        for (Calendar calendar = startDate; calendar.before(endDate); calendar.add(Calendar.DATE, 1)) {
            String fileName = properties.getProperty("DATA_PATH");
            System.out.println("in for loop");
            if (isWeekDay(calendar)) {
                try{
                    String genFileName = createFilenamGivenDate(calendar);
                    File file = new File(fileName + genFileName);
                    if (file.exists()) {
                        System.out.println(genFileName + " exists!");
                        // Logger.trace(genFileName + " exists!");
                        fileName = fileName + genFileName;
                    } else {
                        System.out.println("downloader called.. ");
                        //  Logger.trace("downloader called.. ");
                        fileName = fileName
                                + createUrlDownloadAndExtractFileGivenDate(calendar);
                    }


                    Price instrPrice = ReadPriceCsv.readPrice(fileName, symbol, (Calendar)calendar.clone());
                    instrument.getPriceList().add(instrPrice);
                } catch (FileNotFoundException e){
                    System.err.println("Public Holiday" + calendar.getTime());
                }

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

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
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
