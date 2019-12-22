package com.lsy.java.test.base;

import java.io.*;
/**
 * 流(Stream)、文件(File)和IO
 * Created by lisiyu on 2019/2/15.
 */
public class AboutStreamIO {
    public static void main(String[] args) throws IOException {
//        bufferedReaderRead();//从控制台读取多字符输入
//        bufferedReaderReadLine();//从控制台读取字符串
//        fileStream();//先创建文件test.txt，并把给定的数字以二进制形式写进该文件，同时输出到控制台上。
//        fileStream();//解决乱码问题
        directory();//目录相关
    }

    static void bufferedReaderRead() throws IOException {
        char c;
        // 使用 System.in 创建 BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("输入字符, 按下 'q' 键退出。");
        // 读取字符
        do {
            c = (char) br.read();
            System.out.println(c);
        } while (c != 'q');
    }

    static void bufferedReaderReadLine() throws IOException {
        // 使用 System.in 创建 BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        System.out.println("Enter lines of text.");
        System.out.println("Enter 'end' to quit.");
        do {
            str = br.readLine();
            System.out.println(str);
        } while (!str.equals("end"));

    }

    static void fileStream() throws FileNotFoundException {
//        InputStream f = new FileInputStream("./hello");
//        File f = new File("./hello");
//        InputStream in = new FileInputStream(f);

//        OutputStream f = new FileOutputStream("./hello");
//        File f2 = new File("./hello");
//        OutputStream out = new FileOutputStream(f2);

        try {
            byte bWrite[] = { 11, 21, 3, 40, 5 };
            OutputStream os = new FileOutputStream("./test.txt");
            for (int x = 0; x < bWrite.length; x++) {
                os.write(bWrite[x]); // writes the bytes
            }
            os.close();

            InputStream is = new FileInputStream("./test.txt");
            int size = is.available();

            for (int i = 0; i < size; i++) {
                System.out.print((char) is.read() + "  ");
            }
            is.close();
        } catch (IOException e) {
            System.out.print("Exception");
        }
    }

    static void fileStream2() throws IOException {
        File f = new File("./a.txt");
        FileOutputStream fop = new FileOutputStream(f);
        // 构建FileOutputStream对象,文件不存在会自动新建

        OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
        // 构建OutputStreamWriter对象,参数可以指定编码,默认为操作系统默认编码,windows上是gbk

        writer.append("中文输入");
        // 写入到缓冲区

        writer.append("\r\n");
        // 换行

        writer.append("English");
        // 刷新缓存冲,写入到文件,如果下面已经没有写入的内容了,直接close也会写入

        writer.close();
        // 关闭写入流,同时会把缓冲区内容写入文件,所以上面的注释掉

        fop.close();
        // 关闭输出流,释放系统资源

        FileInputStream fip = new FileInputStream(f);
        // 构建FileInputStream对象

        InputStreamReader reader = new InputStreamReader(fip, "UTF-8");
        // 构建InputStreamReader对象,编码与写入相同

        StringBuffer sb = new StringBuffer();
        while (reader.ready()) {
            sb.append((char) reader.read());
            // 转成char加到StringBuffer对象中
            System.out.println(sb.toString());
        }

        reader.close();
        // 关闭读取流

        fip.close();
        // 关闭输入流,释放系统资源
    }

    static void directory(){

        String dirname = "/tmp";
        File f1 = new File(dirname);
        if (f1.isDirectory()) {
            System.out.println("目录 " + dirname);
            String s[] = f1.list();
            for (int i = 0; i < s.length; i++) {
                File f = new File(dirname + "/" + s[i]);
                if (f.isDirectory()) {
                    System.out.println(s[i] + " 是一个目录");
                } else {
                    System.out.println(s[i] + " 是一个文件");
                }
            }
        } else {
            System.out.println(dirname + " 不是一个目录");
        }

        dirname = "/tmp/test123";
//        File d = new File(dirname);
////        // 现在创建目录
//        d.mkdirs();
//        d = new File(dirname+"/222");
//        d.mkdirs();

        File folder = new File(dirname);
        deleteFolder(folder);

    }

    // 删除文件及目录
    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
}
