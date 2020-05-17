import com.lsy.java.test.utils.IdGen;
import com.lsy.java.test.utils.MD5Utils;

import java.util.UUID;

/**
 * Created by lisiyu on 2020/3/30.
 */
public class TestMethod {
    public static void main(String[] args) {
        System.out.println(IdGen.uuid());
        System.out.println(IdGen.nextId());
        System.out.println(IdGen.getUUID());
        String a = UUID.randomUUID().toString();
        System.out.println(a);
        System.out.println(MD5Utils.md5(a));
        System.out.println(MD5Utils.md5(a));
//        System.out.println(UUID.fromString(a));
    }
}
