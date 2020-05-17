package com.lsy.test.spring.command;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static java.lang.System.exit;

/**
 * Created by lisiyu on 2020/4/9.
 */
@SpringBootApplication
public class ConsoleApplication implements CommandLineRunner {

    public static void main(String[] args)  {
        //disabled banner, don't want to see the spring logo
        SpringApplication app = new SpringApplication(ConsoleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

//        new SpringApplicationBuilder(ConsoleApplication.class)
//                .web(WebApplicationType.NONE).run(args); // .REACTIVE, .SERVLET
    }

    @Override
    public void run(String... args) {
        System.out.println("enter run.");
        if (args.length > 0) {
            System.out.println(args[0]);
        } else {
            System.out.println("no args.");
        }

        exit(0);

    }
}
