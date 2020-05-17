package com.lsy.test.spring.yaml;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 通过yaml绑定，注入数据的模型
 */
@Data
@Component
@ConfigurationProperties(prefix = "teather")
public class Teacher {
    @Value("小红") //单个赋值，优先级低
    private String ip;
    //    @Value("${teather.age}")  // 使用SpringEl读取配置文件中的值并注入
    private String age;
    private Date birthday;
    private Boolean gender;
    private String[] hobbies; //集合处理方式和数组相同
    // {省:江苏,市:南京}
    private Map<String, Object> location;

}
