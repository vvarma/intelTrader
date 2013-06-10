package com.inteltrader.advisor;

import com.inteltrader.entity.Price;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 1/5/13
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Advisor {
    Advice getAdvice();
    Advice updatePriceAndGetAdvice(Price price) throws IOException;
}
