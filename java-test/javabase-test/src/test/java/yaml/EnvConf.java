package yaml;

import com.lsy.java.test.object.Context;
import com.lsy.java.test.utils.YamlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lisiyu on 2016/11/17.
 * 环境配置信息
 */
public class EnvConf {
    private static final Logger LOG = LoggerFactory.getLogger(EnvConf.class);
    private static Yaml yaml = new Yaml();
    public static Map config = new HashMap();
    private static String yaml_file = "conf/src/main/resources/dmp-env-conf.yaml";

    /**
     * 常量
     * */
    // HBASE 常量
//    public static String HBASE_SITE_XML = "example/hbase-site-test.xml";
    public static String HBASE_USER_NAME = "";
    public static String HBASE_ZK_QUORUM = "x:2181,x:2181";
    public static String HBASE_ZK_ZNODE_PARENT = "/hbase";
    public static String HBASE_FS_TMP_DIR = "/user/its_bi/hbase-staging";
    public static int HBASE_CONNECTION_THREAD_MAX = 1000;
    public static int HBASE_CONNECTION_THREAD_MIN = 1000;
    public static int HBASE_CONNECTION_THREAD_KEEPALIVETIME = 300;
    public static Long HBASE_TABLE_WRITE_BUFFER_SIZE = 50L*1024*1024;  // 50MB
    public static boolean HBASE_TABLE_AUTO_FLUSH = false;
    public static boolean HBASE_TABLE_AUTO_FLUSH_FAIL_RETRY = false;
    public static boolean HBASE_TABLE_SET_WRITE_TO_WAL = false;
    // hbase - table: traj-order
    public static String HBASE_TABLE_TRAJ_ORDER = "STS_DMP:ORDER_TRAJ";
    public static String HBASE_TABLE_TRAJ_ORDER_CF = "T";
    public static String HBASE_TABLE_TRAJ_ORDER_COLUMN = "c";
    // hbase - table: traj-link
    public static String HBASE_TABLE_TRAJ_LINK = "STS_DMP:LINK_ORDER";
    public static String HBASE_TABLE_TRAJ_LINK_CF = "T";
    public static String HBASE_TABLE_TRAJ_LINK_COLUMN = "c";

    // HADOOP 常量
    public static String HDFS_CITY_ORDER_GPS = "/user/its_bi/cityordergps/";

    // HIVE 常量
    public static String HIVE_DB_ITS = "its_bi";
    public static String HIVE_DB_BI = "gulfstream_dwd";
    public static String HIVE_TABLE_BI_ORDER = "dwd_order_make_d";

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
            LOG.debug("EnvConf.setYamlConfig(),ts="+System.currentTimeMillis());
            config = conf;
//            Context TrajContext = new Context((Map)(config).get("traj"));
            Context HBaseContext = new Context((Map)(config).get("hbase"));
            Context HiveContext = new Context((Map)(config).get("hive"));
            Context HadoopContext = new Context((Map)(config).get("hadoop"));

//            HBASE_SITE_XML = HBaseContext.get("site.xml", String.class);
            HBASE_USER_NAME = HBaseContext.get("hbase.user.name", String.class);
            HBASE_ZK_QUORUM = HBaseContext.get("hbase.zookeeper.quorum", String.class);
            HBASE_ZK_ZNODE_PARENT = HBaseContext.get("zookeeper.znode.parent", String.class);
            HBASE_FS_TMP_DIR = HBaseContext.get("hbase.fs.tmp.dir", String.class);
            HBASE_CONNECTION_THREAD_MAX = HBaseContext.get("hbase.hconnection.threads.max", Integer.class);
            HBASE_CONNECTION_THREAD_MIN = HBaseContext.get("hbase.hconnection.threads.min", Integer.class);
            HBASE_CONNECTION_THREAD_KEEPALIVETIME = HBaseContext.get("hbase.hconnection.threads.keepalivetime", Integer.class);
            HBASE_TABLE_TRAJ_ORDER = HBaseContext.get("table.traj.order", String.class);
            HBASE_TABLE_TRAJ_ORDER_CF = HBaseContext.get("table.traj.order.cf", String.class);
            HBASE_TABLE_TRAJ_ORDER_COLUMN = HBaseContext.get("table.traj.order.column", String.class);
            HBASE_TABLE_WRITE_BUFFER_SIZE = HBaseContext.get("table.write.buffer.size", Integer.class)*1L;
            HBASE_TABLE_AUTO_FLUSH = HBaseContext.get("table.auto.flush", boolean.class);
            HBASE_TABLE_AUTO_FLUSH_FAIL_RETRY = HBaseContext.get("table.auto.flush.fail.retry", boolean.class);
            HBASE_TABLE_SET_WRITE_TO_WAL = HBaseContext.get("table.set.write.to.wal", boolean.class);
            HBASE_TABLE_TRAJ_LINK = HBaseContext.get("table.traj.link", String.class);
            HBASE_TABLE_TRAJ_LINK_CF = HBaseContext.get("table.traj.link.cf", String.class);
            HBASE_TABLE_TRAJ_LINK_COLUMN = HBaseContext.get("table.traj.link.column", String.class);

            HDFS_CITY_ORDER_GPS = HadoopContext.get("city.order.gps.hdfs", String.class);

            HIVE_DB_ITS = HiveContext.get("its.database", String.class);
            HIVE_DB_BI = HiveContext.get("lsy.bi.database", String.class);
            HIVE_TABLE_BI_ORDER = HiveContext.get("lsy.bi.order.table", String.class);


        }
    }






}
