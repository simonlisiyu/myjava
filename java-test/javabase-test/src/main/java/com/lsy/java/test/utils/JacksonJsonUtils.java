//package com.lsy.java.test.utils;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import net.sf.json.JSONObject;
//import net.sf.json.JsonConfig;
//import net.sf.json.util.PropertyFilter;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Jackson and JSON
// * Created by lisiyu on 2017/3/30.
// *
// * pom:
// *
// *
// <dependency>
// <groupId>net.sf.json-lib</groupId>
// <artifactId>json-lib</artifactId>
// <classifier>jdk15</classifier>
// </dependency>
//
// <dependency>
// <groupId>commons-lang</groupId>
// <artifactId>commons-lang</artifactId>
// <version>2.6</version>
// </dependency>
// <dependency>
// <groupId>net.sf.ezmorph</groupId>
// <artifactId>ezmorph</artifactId>
// </dependency>
// <dependency>
// <groupId>commons-collections</groupId>
// <artifactId>commons-collections</artifactId>
// </dependency>
// <dependency>
// <groupId>commons-beanutils</groupId>
// <artifactId>commons-beanutils</artifactId>
// </dependency>
//
// <dependency>
// <groupId>com.fasterxml.jackson.core</groupId>
// <artifactId>jackson-databind</artifactId>
// </dependency>
// */
//@Service
//@Lazy
//public class JsonUtils {
//    // reuse the object mapper to save memory footprint
//    public static final ObjectMapper mapper = new ObjectMapper();
//    private static final ObjectMapper indentMapper = new ObjectMapper();
//    private static final ObjectMapper typeMapper = new ObjectMapper();
//    public static JsonConfig jsonConfig = new JsonConfig();
//
//    public static PropertyFilter filter = new PropertyFilter() {
//        public boolean apply(Object object, String fieldName, Object fieldValue) {
//            return null == fieldValue;
//        }
//    };
//    static {
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
////        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//        indentMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//        typeMapper.enableDefaultTyping();
//
//        jsonConfig.setJsonPropertyFilter(filter);
//    }
//
//    public static Map<String, String> json2Map(JSONObject jsonObject){
//        Map<String, String> map = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//
//        try{
//            map = mapper.readValue(jsonObject.toString(), new TypeReference<HashMap<String,String>>(){});
//        }catch(Exception e){}
//        return map;
//    }
//
//    /**
//     * 转成json时，去掉无值的参数
//     */
////    private static void testJsonFilter(){
////        EventInfo t = new EventInfo();
////        t.setEvent_id("123");
////
//////        JsonConfig jsonConfig = new JsonConfig();
//////        PropertyFilter filter = new PropertyFilter() {
//////            public boolean apply(Object object, String fieldName, Object fieldValue) {
//////                return null == fieldValue;
//////            }
//////        };
//////        jsonConfig.setJsonPropertyFilter(filter);
////        System.out.println(JSONObject.fromObject(t, JsonUtils.jsonConfig).toString());
////    }
//
////    public static void main(String[] args) {
////        testJsonFilter();
////    }
//}
