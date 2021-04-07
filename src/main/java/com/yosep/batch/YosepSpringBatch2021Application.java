package com.yosep.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class YosepSpringBatch2021Application {
    public static void main(String[] args) {
        SpringApplication.run(YosepSpringBatch2021Application.class, args);
    }

}
