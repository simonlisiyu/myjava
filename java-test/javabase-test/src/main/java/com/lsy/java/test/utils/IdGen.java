package com.lsy.java.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Random;
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


    private static  final int SHORT_MAX=65536;
    private static int counter=-1;
    /**
     * Creates a unique 64 bits ID by aggregating the current time in
     * milliseconds since epoch (Jan. 1, 1970) and using a 16 bits counter. The
     * counter is initialized at a random number. This generator can create up
     * to 65536 different id per millisecond.
     *
     * @return a new id.
     */
    public static synchronized long nextId() {
        long now = System.currentTimeMillis();
        if (counter == -1) {
            long seed = now ^ Thread.currentThread().getId();
            Random rnd = new Random(Long.hashCode(seed));
            counter = rnd.nextInt(SHORT_MAX);
        }
        long id = (now << 16) | counter;
        counter = (counter + 1) % SHORT_MAX;
        return id;
    }

    /**
     * generate uniq uuid
     * @return
     */
    public static synchronized String getUUID(){
        String s= UUID.randomUUID().toString();
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
}
