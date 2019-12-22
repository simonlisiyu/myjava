package com.lsy.java.test.advance.inheritance;

/**
 * Created by lisiyu on 2019/2/14.
 */
public class BankVaultDoor extends Door {
    // The "extends" keyword used to tell java that BankVaultDoor inherits the functionality of Door.

    public void open () {
        System.out.println("密码打开");
    }

}
