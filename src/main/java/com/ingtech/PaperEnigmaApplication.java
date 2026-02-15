package com.ingtech;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PaperEnigmaApplication {

    static void main(String[] args) {
        var app = new SpringApplication(PaperEnigmaApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
//        SpringApplication.run(PaperEnigmaApplication.class, args);
    }

}
