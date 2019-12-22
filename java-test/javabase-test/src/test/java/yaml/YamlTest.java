package yaml;

import com.lsy.java.test.object.Context;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lisiyu on 2017/2/3.
 */
public class YamlTest {
    public static void main(String[] args) {
        Yaml yaml = new Yaml();
        File f=new File("conf/src/main/resources/rules111.yaml");

        Map result= null;
        try {
            result = (Map) yaml.load(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Context context = new Context((Map)(result).get("monitor"));
        List<String> list = context.get("items.name", new ArrayList<String>().getClass(), null);
//        Set<String> set = context.get("items.nameset", new HashSet<String>().getClass(), null);
//        String i = JStormContext.get("ccc.ffff", String.class, null);
        System.out.println(list);

        Map<String, Context> YAML_KEY_DETAILS_MAP = new HashMap<>();    // yaml key name作为key的map，value为context
        for(String keyName : list){
            Map map = context.get(keyName, Map.class, null);
            Context details = new Context(map);
            YAML_KEY_DETAILS_MAP.put(keyName, details);
        }

        System.out.println(YAML_KEY_DETAILS_MAP.get("ip.ping"));
//        System.out.println(set);
//        Map str = context.get("disk.usage", Map.class, null);
////        String i = JStormContext.get("ccc.ffff", String.class, null);
//        System.out.println(str);
//        System.out.println(str.get("1").getClass());
//
//
//        List<String> items = context.get("items.name", new ArrayList<String>().getClass(), null);
//        for(String item : items){
//            System.out.println("item="+item);
//            ArrayList<String[]> arrayList = new ArrayList<>();
//            Map map = context.get(item, Map.class, null);
//            System.out.println("map="+map);
//            for(int m=1; m<=map.size(); m++){
//                System.out.println("m="+m);
//                List<Object> list11 = (List)map.getOrDefault(m+"", null);
//                System.out.println("list11="+list11);
//                if(list11 != null){
//                    String[] strings = new String[10];
//                    for(int i=0; i<list11.size(); i++){
//                        System.out.println("i="+i);
//                        strings[i] = list11.get(i).toString();
//                    }
//                    arrayList.add(strings);
//                }
//            }
//
//        }
//
//        Map map = new HashMap();
//        Map valueMap = (Map)(result).get("mjdos");
//        Map a = (Map)valueMap.getOrDefault("tcp.connections", null);
//        ArrayList arrayList = new ArrayList();
//        arrayList.add(10240);
//        arrayList.add(0);
//        arrayList.add(1);
//        arrayList.add(0);
//        arrayList.add("TCP连接数12223");
//        arrayList.add(0);
//        arrayList.add("");
//        arrayList.add("");
//        arrayList.add("");
//        a.put("1", arrayList);
//        valueMap.put("tcp.connections", a);
//        map.put("mjdos", valueMap);
//        try {
//            yaml.dump(map, new FileWriter(f));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
