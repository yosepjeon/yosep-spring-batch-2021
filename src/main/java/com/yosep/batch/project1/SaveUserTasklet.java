package com.yosep.batch.project1;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.List;

public class SaveUserTasklet implements Tasklet {
    private final int SIZE = 10_000;
    private final UserRepository userRepository;

    public SaveUserTasklet(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        return null;
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            users.add(User.builder()
                    .totalAmount(1_000)
                    .userName("test username" + i)
                    .build());
        }

        for (int i = 100; i < 200; i++) {
            users.add(User.builder()
                    .totalAmount(200_000)
                    .userName("test username" + i)
                    .build());
        }

        for (int i = 200; i < 300; i++) {
            users.add(User.builder()
                    .totalAmount(300_000)
                    .userName("test username" + i)
                    .build());
        }

        for (int i = 300; i < 400; i++) {
            users.add(User.builder()
                    .totalAmount(500_000)
                    .userName("test username" + i)
                    .build());
        }

        return users;
    }
}
