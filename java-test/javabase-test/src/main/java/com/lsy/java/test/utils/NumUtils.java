package com.lsy.java.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lisiyu on 2017/5/16.
 */
public class NumUtils {
    private static final Logger LOG = LoggerFactory.getLogger(NumUtils.class);
    /**
     * @param num:要获取二进制值的数
     * @param index:倒数第一位为0，依次类推
     */
    public static int get(int num, int index)
    {
        return (num & (0x1 << index)) >> index;
    }

    public static int getStartPosition(int start){
        if(start < 0){
            LOG.warn("start is smaller than 0, start="+start);
            start = 0;
        }
        return start;
    }

    public static int getLengthValid(int length, int maxLength){
        if(length > maxLength){
            LOG.warn("length is bigger than maxLength, length="+length+", maxLength="+maxLength);
            length = maxLength;
        }
        return length;
    }

    public static long getStartPosition(long start){
        if(start < 0){
            LOG.warn("start is smaller than 0, start="+start);
            start = 0;
        }
        return start;
    }

    public static long getLengthValid(long length, long maxLength){
        if(length > maxLength){
            LOG.warn("length is bigger than maxLength, length="+length+", maxLength="+maxLength);
            length = maxLength;
        }
        return length;
    }

}
