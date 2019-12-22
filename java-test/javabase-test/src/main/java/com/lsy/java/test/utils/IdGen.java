package com.lsy.java.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Created by lisiyu on 2016/11/10.
 */
public abstract class IdGen {
    private static final Logger LOG = LoggerFactory.getLogger(IdGen.class);

    @SuppressWarnings("unused")
    private static SecureRandom random = new SecureRandom();

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }


}
