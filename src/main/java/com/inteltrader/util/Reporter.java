package com.inteltrader.util;

import com.inteltrader.advisor.Advice;
import com.inteltrader.entity.Investment;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 7/23/13
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Aspect
public class Reporter {

    PortfolioReportMaker reportMaker;

    public Reporter() throws IOException {
        reportMaker = new PortfolioReportMaker();
    }


  //  @AfterReturning("execution(* com.inteltrader.service.PortfolioService.createPortfolio(..))")
    public void afterPortfolioCreation(JoinPoint point) {
        System.out.println("After Portfolio");
        Object[] args = point.getArgs();
        String portfolioName = null;
        if (args[0] instanceof String) {
            portfolioName = (String) args[0];
        } else {
            System.out.println("unexpected Args");
        }
        if (portfolioName!=null){
            reportMaker.createReportFileIfNoExist(portfolioName);
        }

    }


    public void onPortfolioUpdate(JoinPoint point) {
        System.out.println("After Portfolio");
        for (Object o : point.getArgs()) {
            System.out.println(o.getClass());
        }
        System.out.println(point.toShortString());
        System.out.println(point.getSourceLocation());
        System.out.println(point.getKind());
        System.out.println(point.getTarget().getClass());
        System.out.println(point.getSignature());
    }
 //   @AfterReturning("execution(* com.inteltrader.service.PortfolioService.addToPortfolio(..))")
    public void onAddingInvestment(JoinPoint point) {
        System.out.println("After Adding Investment");
        Object[] args=point.getArgs();
        String portfolioName=null;
        String symbolName=null;
        if (args[0] instanceof String && args[1]instanceof String) {
            portfolioName = (String) args[0];
            symbolName=(String) args[1];
        } else {
            System.out.println("unexpected Args");
        }
        if (portfolioName!=null&&symbolName!=null)
        reportMaker.createReportFileIfNoExist(portfolioName);
    }

    public void afterMakingInvestment(JoinPoint point) throws IOException {
        System.out.println("After Portfolio");
        System.out.println(point.getArgs());
        System.out.println(point.getKind());
        System.out.println(point.getTarget().getClass());
        System.out.println(point.getSignature());


    }
}

  class PortfolioReportMaker {

    String path;


    PortfolioReportMaker() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("intel.properties"));
        path = properties.getProperty("REPORT_PATH");
    }
    public boolean createReportFileIfNoExist(String portfolioName){
        String fileName=path+"\\"+portfolioName+".xls";
        String title=portfolioName.toUpperCase()+"-REPORT";
        try {
            File file=new File(fileName);
            if (file.exists()){
                Workbook workbook;
                workbook=Workbook.getWorkbook(file);
                Sheet sheet=workbook.getSheet(0);
                String s=sheet.getCell(0,2).getContents();
                if (s.equalsIgnoreCase(title)){   {
                    System.out.println("Hi");
                    return true;
                }

                }
            }
            WritableWorkbook writableWorkbook=Workbook.createWorkbook(new File(fileName));
            WritableSheet sheet=writableWorkbook.createSheet(portfolioName,0);
            Label label=new Label(0,2,title);
            sheet.addCell(label);
            writableWorkbook.write();
            writableWorkbook.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (BiffException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (RowsExceededException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (WriteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return true;
    }
      public boolean addInvestmentsToReport(String portfolioName,String symbolName){
          String fileName=path+"\\"+portfolioName+".xls";

          return true;
      }

}
