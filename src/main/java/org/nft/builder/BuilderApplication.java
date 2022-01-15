package org.nft.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuilderApplication {

    private static Logger LOGGER = LoggerFactory.getLogger(BuilderApplication.class);

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(BuilderApplication.class);
    }

}
