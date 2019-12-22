package yaml;

/**
 * Created by lisiyu on 2018/5/8.
 */
public class TrajEnvConfTest {


    public static void main(String[] args) {
        EnvConf.initConf("conf/src/main/resources/dmp-env-conf.yaml");
        System.out.println(EnvConf.HBASE_ZK_QUORUM);
    }
}
