package com.inteltrader.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 6/19/13
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeforeAndAfterLogger {
    private Logger logger=Logger.getLogger(this.getClass());
    public Object aroundMethod(ProceedingJoinPoint point) throws Throwable {
        Object retVal = null;

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        try {
            logger.trace("Calling :" + method.getName() + "(" + signature + ")");

            long startTime = System.currentTimeMillis();
            retVal = point.proceed();
            long stopTime = System.currentTimeMillis();

           logger.trace("Returning :" + method.getName() + "(" + signature + ")"
                    + ". Took " + String.valueOf(stopTime - startTime) + " ms.");
        } catch (Throwable throwable) {

            logger.error("Error :" + point.toString() + " threw exception = "+throwable.toString());
            throw throwable;
        }

        return retVal;

    }
}
