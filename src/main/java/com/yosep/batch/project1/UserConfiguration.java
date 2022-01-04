package com.yosep.batch.project1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@Slf4j
public class UserConfiguration {
    private final String JOB_NAME = "userJob";
    private final int CHUNK = 1000;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final UserRepository userRepository;
    private final EntityManagerFactory entityManagerFactory;

    public UserConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.userRepository = userRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public Job userJob() throws Exception {
        return this.jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(this.saveUserStep())
                .next(this.userLevelUpStep())
                .listener(new LevelUpJobExecutionListener(userRepository))
                .build();
    }

    @Bean
    public Step saveUserStep() {
        return this.stepBuilderFactory.get("saveUserStep")
                .tasklet(new SaveUserTasklet(userRepository))
                .build();
    }

    @Bean
    public Step userLevelUpStep() throws Exception{
        return this.stepBuilderFactory.get("userLevelUpStep")
                .<User, User>chunk(CHUNK)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private ItemReader<? extends User> itemReader() throws Exception {
        JpaPagingItemReader<User> itemReader = new JpaPagingItemReaderBuilder<User>()
                .queryString("select u from User u")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK)
                .name(JOB_NAME + "_userItemReader")
                .build();

        itemReader.afterPropertiesSet();

        return itemReader;
    }

    private ItemProcessor<? super User, ? extends User> itemProcessor() throws Exception {
        return user -> {
            if(user.availableLeveUp()) {
                return user;
            }

            return null;
        };
    }

    private ItemWriter<? super User> itemWriter() {
        return users -> users.forEach(u -> {
            u.levelUp();
            userRepository.save(u);
        });
    }
}
