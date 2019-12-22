package com.lsy.java.test.advance;

/**
 * Created by lisiyu on 2019/2/14.
 */
public class TryCatch {
    public static void main(String[] args) {
        try {
            //Code here
        } catch (Exception name) {
            //Replace ExceptionHere with your exception and name with the name of your exception.
            //Code if exception "ExceptionHere" is thrown.

            throw new IllegalArgumentException("Number not above 0");
            /* Will print
                Exception in thread "Main": java.lang.IllegalArgumentException: Number not above 0
            */
        }
    }
}
