package org.nft.builder.models;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DefaultFeatures {

    @Bean
    public Feature head() {
        return new Feature("HEAD", 60, 10, 2);
    }

    @Bean
    public Feature torso() {
        return new Feature("TORSO", 60, 60, 1);
    }

    @Bean
    public Feature arms() {
        return new Feature("ARMS", 10, 125, 0);
    }

    @Bean
    public Feature legs() {
        return new Feature("LEGS", 75, 230, 0);
    }

    @Bean
    public Feature background() {
        return new Feature("BACKGROUND", 0, 0, -1);
    }

}
