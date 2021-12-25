package org.nft.builder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuilderApplication.class);
//        SpringApplicationBuilder builder = new SpringApplicationBuilder(BuilderApplication.class);
//        builder.headless(false);
//        ConfigurableApplicationContext context = builder.run(args);
    }

}
