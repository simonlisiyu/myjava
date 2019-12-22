package yaml;

import com.lsy.java.test.object.Context;
import com.lsy.java.test.utils.YamlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lisiyu on 2018/5/8.
 * 系统变量信息
 */
public class SystemConstant {
    private static final Logger LOG = LoggerFactory.getLogger(SystemConstant.class);
    private static Yaml yaml = new Yaml();
    private static Map config = new HashMap();
    public static Context ConstContext = null;
    private static String yaml_file = "conf/src/main/resources/dmp-traj-const.yaml";

    /**
     * 常量
     * */
    // HBASE 常量
    public static String HBASE_ROWKEY_SPLIT = "_";
    public static String HBASE_VALUES_SPLIT = ",";
    public static String HBASE_VALUE_SPLIT = ":";
    public static int HBASE_SCAN_PARTITION_SIZE = 1;
    public static int HBASE_MULTIGET_PARTITION_SIZE = 10;

    // REST 常量
    public static int REST_SERVER_THREAD_POOL_SIZE = 250;

    /**
     * 从yaml配置文件中读取conf，更新静态变量
     */
    public static void initConf() {
        initConf(yaml_file);
    }
    public static void initConf(String yamlFileName) {
        config = YamlUtils.getYamlConfig(yamlFileName, config);
        setYamlConfig(config);
    }

    private static void setYamlConfig(Map conf) {
        synchronized (config) {
            LOG.debug("SystemConstant.setYamlConfig(),ts="+System.currentTimeMillis());
            config = conf;
            ConstContext = new Context((Map)(config).get("const"));

            HBASE_ROWKEY_SPLIT = ConstContext.get("hbase.rowkey.split", String.class);
            HBASE_VALUES_SPLIT = ConstContext.get("hbase.values.split", String.class);
            HBASE_SCAN_PARTITION_SIZE = ConstContext.get("hbase.scan.partition.size", Integer.class);
            HBASE_MULTIGET_PARTITION_SIZE = ConstContext.get("hbase.multiget.partition.size", Integer.class);
            if(ConstContext.containsKey("rest.server.thread.pool.size")) REST_SERVER_THREAD_POOL_SIZE = ConstContext.get("rest.server.thread.pool.size", Integer.class);

        }
    }
}
