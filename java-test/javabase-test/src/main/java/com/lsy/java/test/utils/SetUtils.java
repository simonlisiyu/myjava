package com.lsy.java.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by lisiyu on 2018/5/24.
 * 集合类工具
 */
public class SetUtils {
    private final static Logger LOG = LoggerFactory.getLogger(SetUtils.class);

    /**
     * by from and size, return String[]
     * @param set
     * @param from
     * @param size
     * @return
     */
    public static String[] getArrWithSize(Set<String> set, int from, int size){
        LOG.info("set.size="+set.size()+", from="+from+", size="+size);
        String[] resArr;
        String[] totalArr = set.toArray(new String[0]);
        long totalSize = set.size();
        int afterFromSize = (int)totalSize - from;
        if(afterFromSize <= 0){
            return new String[0];
        }
        int end = from + size;
        if(afterFromSize < size){
            end = afterFromSize+from;
        }
        resArr = new String[end-from];
        for(int i=from;i<end;i++){
            resArr[i-from] = totalArr[i];
        }
        return resArr;
    }
}
