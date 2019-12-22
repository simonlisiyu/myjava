package com.lsy.other;


import org.apache.commons.lang3.StringUtils;

/**
 * 邮件格式脱敏
 * Created by lisiyu on 2019/8/16.
 */
public class EmailDesensitization {

    /**
     * 写个函数，把下面字符中的邮箱地址脱敏成******格式
     * 待匹配字符：今天天气很好，我发了个邮件给abc@163.com,10086@alibaba-inc.com和10086_abc@gmail.cn
     */
    public static void main(String[] args){
        String content = "今天天气很好，我发了个邮件给abc@163.com,10086@alibaba-inc.com和10086_abc@gmail.cn";
        System.out.println(emailDesensitization(content));
    }

    /**
     * 邮件格式脱敏
     *
     * @param content
     *  ：要脱敏的内容
     * @return
     *  ：脱敏后的内容
     */
    public static String emailDesensitization(String content){
        if(StringUtils.isEmpty(content)){
            return "要脱敏的数据不能为空";
        }
        char[] chars = content.toCharArray();
        // 无多线程环境
        StringBuilder stringBuilder = new StringBuilder();
        boolean isDesensitization = false;
        for (int i = 0; i < chars.length ; i++) {
            if("给".toCharArray()[0] == chars[i]){
                isDesensitization = true;
                stringBuilder.append(chars[i]);
                continue;
            }
            // 不需要脱敏的字符【这里是可变的部分，还可以再优化一下。变与不变分离】
            if("，".toCharArray()[0] == chars[i] ||
                    ",".toCharArray()[0] == chars[i] ||
                    "和".toCharArray()[0] == chars[i]){
                stringBuilder.append(chars[i]);
                continue;
            }
            if(isDesensitization){
                stringBuilder.append("*");
                continue;
            }
            stringBuilder.append(chars[i]);

        }
        return stringBuilder.toString();
    }

}
