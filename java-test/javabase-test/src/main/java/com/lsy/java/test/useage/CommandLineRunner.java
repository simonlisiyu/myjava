//package com.lsy.java.test.useage;
//
//import org.apache.commons.cli.CommandLineParser;
//import org.apache.commons.cli.BasicParser;
//import org.apache.commons.cli.Options;
//import org.apache.commons.cli.CommandLine;
//
///**
// * 命令行
// * Created by lisiyu on 2019/2/28.
// */
//public class CommandLineRunner {
//
//    public static void main(String[] args) {
//        try {
//            // Create a Parser
//            CommandLineParser parser = new BasicParser( );
//            Options options = new Options( );
//            options.addOption("h", "help", false, "help");
//            options.addOption("t", "inputtype", true, "mysql or shp" );
//            options.addOption("l", "inputlink", true, "input mysql connection or file-url");
//            options.addOption("n", "tablename", true, "geomesa table name");
//            options.addOption("p", "typename", true, "geomesa type name");
//            options.addOption("g", "geotype", true, "Point or LineString or MultiLineString");
//            options.addOption("f", "featureid", true, "feature id");
//            options.addOption("e", "ecql", true, "select a,b,c from inputname where ECQL");
//            options.addOption("d", "date", true, "date: yyyyMMddhh format");
//            // Parse the program arguments
//            CommandLine commandLine = parser.parse(options, args);
//
//            String inputType = "";  //"mysql"
//            String inputLink = "";  //"100.90.78.53:8003 quick_fix quick_fix@123 quick_fix c_2018050912","/home/zhali/road.shp"
//            String tableName = "";  //"traffic"
//            String typeName = "";  //"c_2018050912_test"
//            String geomType = "";  //"Point"
//            String featureid = ""; //"id"
//            String ecql = "";  //"select geom,id,passage,origin from c_2018050912 where passage = 2"
//            String ds = "";  //"2018050912"
//
//            if (commandLine.hasOption('h')) {
//                System.out.print( "  -h(help) \n" +
//                        "  -t(inputtype) mysql or shp \n" +
//                        "  -l(inputlink) mysql connection or file-url \n" +
//                        "  -n(tablename) geomesa table name \n" +
//                        "  -p(typename) geomesa type name\n" +
//                        "  -g(geotype) Point or LineString or MultiLineString\n" +
//                        "  -f(featureid) feature id\n" +
//                        "  -e(ecql) select a,b,c from inputname where ECQL\n" +
//                        "  -d(date) yyyyMMddhh\n" +
//                        "  demo: -t mysql -l 'host:port username password database table' -n traffic -p geotest -g Point -e 'select a,b,c from table where id<10' -d 2018050912");
//                System.exit(0);
//            }
//            if (commandLine.hasOption('t')) {
//                inputType = commandLine.getOptionValue('t');
//            }
//            if (commandLine.hasOption('l')) {
//                inputLink = commandLine.getOptionValue('l');
//            }
//            if (commandLine.hasOption('n')) {
//                tableName = commandLine.getOptionValue('n');
//            }
//            if (commandLine.hasOption('p')) {
//                typeName = commandLine.getOptionValue('p');
//            }
//            if (commandLine.hasOption('g')) {
//                geomType = commandLine.getOptionValue('g');
//            }
//            if (commandLine.hasOption('f')) {
//                featureid = commandLine.getOptionValue('f');
//            }
//            if (commandLine.hasOption('e')) {
//                ecql = commandLine.getOptionValue('e');
//            }
//            if (commandLine.hasOption('d')) {
//                ds = commandLine.getOptionValue('d');
//            }
//
//
//            System.out.println(tableName);
//            System.out.println(typeName);
//            System.out.println(geomType);
//            System.out.println(featureid);
//            System.out.println(ecql);
//            System.out.println(ds);
//            System.out.println(inputType);
//            System.out.println(inputLink);
//            System.out.println("done...");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
