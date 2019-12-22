package com.lsy.java.test.base;

/**
 * Created by lisiyu on 2019/1/29.
 * 变量和基础类型
 * Java是一种强类型语言，这意味着我们在使用变量前需要先定义它们的类型。
 */
public class CommonVariable {

    public static void main(String[] args) {
        //number, 1 byte, [-128,127]
        byte oneByte = -128;
        oneByte = 127;

        //number, 2 bytes, [-32768, 32767]
        short oneShort = -32768;
        oneShort = 32767;

        //number, 4 bytes, [-2147483648, 2147483647] 2GB=2,147,483,647
        int oneInt = -2147483648;
        oneInt = 2147483647;

        //number, 8 bytes, [-9223372036854775808L, 9223372036854775807L] 9,223,372,036,854,775,807
        long oneLong = -9223372036854775808L;
        oneLong = 9223372036854775807L;

        //单精度浮点数 float number, 4 bytes=32bit
        /**
         * float：1bit（符号位）+8bits（指数位+23bits（尾数位）
         * float的指数范围为-127~128
         * float的数字范围为-2^128 ~ +2^128  =  -3.40E+38 ~ +3.40E+38
         *
         * value of floating-point = significand x base ^ exponent , with sign
         * （浮点）数值 =      尾数    ×    底数 ^ 指数，（附加正负号）
         *
         * float：2^23 = 8388608，共七位，意味着最多能有7位有效数字，但绝对能保证的为6位，也即float的精度为6~7位有效数字；
         */
//        float f = (float) 4.5;
        float f = 4.599999f;    //小数点后6位
        System.out.println("f="+f);
        f = 4.1234567f;    //小数点后7位
        System.out.println("f="+f);

        /**
         * float遵从的是IEEE R32.24 ,而double 遵从的是R64.53
         * 无论是单精度还是双精度在存储中都分为三个部分：
         1.符号位(Sign) : 0代表正，1代表为负
         2.指数位（Exponent）:用于存储科学计数法中的指数数据，并且采用移位存储
         3.尾数部分（Mantissa）：尾数部分

         R32.24和R64.53的存储方式都是用科学计数法来存储数据的,
         比如8.25用十进制的科学计数法表示就为:8.25*10e0,而120.5可以表示为:1.205*10e2,
         */

        //单精度浮点数 float number, 4 bytes
        /**
         * double：1bit（符号位）+ 11bits（指数位）+ 52bits（尾数位）
         * double的指数范围为-1023~1024
         * double的数字范围为-2^1024 ~ +2^1024，=  -1.79E+308 ~ +1.79E+308
         *
         * value of floating-point = significand x base ^ exponent , with sign
         * （浮点）数值 =      尾数    ×    底数 ^ 指数，（附加正负号）
         *
         * double：2^52 = 4503599627370496，一共16位，同理，double的精度为15~16位
         */
        double d = 4.123456789;    //小数点后9位
        System.out.println("d="+d);

        //a character, 2 bytes
        /**
         * Java一个字符是它自己的类型，而不是简单的一个数字，所以它不和其他语言一样把ASCII值放在里面，有一个特殊的语法叫字符类型
         * 字母和数字占一个字节，一个字节8位，中文占2个字节，16位
         * char+char，char+int——类型均提升为int，附值char变量后，输出字符编码表中对应的字符。
         */
        char c = '中';
        char c2 = 111;  //整数:0~65535。十进制、八进制、十六进制均可。输出字符编码表中对应的字符
        char c3='中'+'国';
        System.out.println("c="+c);
        System.out.println("c="+(int)c);
        System.out.println("c2="+c2);
        System.out.println("c2="+(int)c2);
        System.out.println("c3="+(int)c3);
        System.out.println("c3="+c3);

        //Character类中的isWhitespace方法用来判断指定字符是否为空白字符，空白字符包括：空格、tab键、换行
        System.out.println(Character.isWhitespace('c'));
        //空格
        System.out.println(Character.isWhitespace(' '));
        //换行
        System.out.println(Character.isWhitespace('\n'));
        //tab键
        System.out.println(Character.isWhitespace('\t'));

        /**
         * 转义序列	描述
         \t	在文中该处插入一个tab键
         \b	在文中该处插入一个后退键
         \n	在文中该处换行
         \r	在文中该处插入回车
         \f	在文中该处插入换页符
         \'	在文中该处插入单引号
         \"	在文中该处插入双引号
         \\	在文中该处插入反斜杠
         */

        /**
         * 序号	方法与描述
         1	isLetter()
         是否是一个字母
         2	isDigit()
         是否是一个数字字符
         3	isWhitespace()
         是否是一个空白字符
         4	isUpperCase()
         是否是大写字母
         5	isLowerCase()
         是否是小写字母
         6	toUpperCase()
         指定字母的大写形式
         7	toLowerCase()
         指定字母的小写形式
         8	toString()
         返回字符的字符串形式，字符串的长度仅为1
         */

        //true or false, 1 byte
        boolean b = true;

    }


}
