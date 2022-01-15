package org.nft.builder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BuilderApplicationTests {

    @BeforeAll
    static void setUp() {
        System.setProperty("java.awt.headless", "false");
    }

    @Test
    void contextLoads() {
    }

}
