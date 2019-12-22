package com.lsy.java.test.advance.inheritance;

/**
 * Created by lisiyu on 2019/2/14.
 */
public class DoorMain {
    public static void main(String[] args) {
        Door d1 = new BankVaultDoor();
        Door d2 = new HouseFrontDoor();
        Door d3 = new CarDoor();

        System.out.println(args[0]);

        if (args[0].equals("car") ) {
            d3.open();
        } else if (args[0].equals("bank")) {
            d1.open();
        } else {
            d2.open();
        }
    }


}
