package com.inteltrader.advisor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vinay
 * Date: 6/4/13
 * Time: 7:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class FIFOList  extends ArrayList{

    private int MAX_COUNT;

    public FIFOList(int MAX_COUNT) {
        super();
        this.MAX_COUNT = MAX_COUNT;

    }

    @Override
    public boolean add(Object o) {
        if(size()<MAX_COUNT-1)
            return super.add(o);
        else {
            remove(0);
            return super.add(o);
        }
    }
}
