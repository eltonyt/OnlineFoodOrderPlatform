package com.elton.foodorder;

import com.elton.foodorder.config.WebMvcConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootApplication
@Import(WebMvcConfig.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("Started");
    }
}
