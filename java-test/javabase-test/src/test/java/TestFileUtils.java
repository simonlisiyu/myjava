import com.lsy.java.test.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by lisiyu on 2020/3/26.
 */
public class TestFileUtils {
    public static void main(String[] args) {
        List<String> sqlList = FileUtils.listFile("/Users/didi/IdeaProjects/dcp-octopus/cli/mysql");

        File file = new File(sqlList.get(0));
        System.out.println(sqlList.get(0));
        BufferedReader reader = null;
        String tempString = null;
        try {
            reader = new BufferedReader(new FileReader(file));

            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if(tempString.charAt(0) == '`'){
                    String[] splits = tempString.replace("`", "").replace("'", "").split(" ");
                    System.out.println("line " + line + ": " + splits[0]+", "+splits[1]+", "+splits[3]);
                }

                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

    }
}
