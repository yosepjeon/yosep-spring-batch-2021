package com.yosep.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableBatchProcessing
@SpringBootApplication
public class YosepSpringBatch2021Application {
    public static void main(String[] args) {
        SpringApplication.run(YosepSpringBatch2021Application.class, args);
    }

}
