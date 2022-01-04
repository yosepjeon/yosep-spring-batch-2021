package com.yosep.batch.project1;


import java.time.LocalDateTime;
import java.util.List;

public interface UserQueryDslRepository {
    long findMinId();

    long findMaxId();

    List<User> findAllByUpdatedDate(LocalDateTime updatedDate);
}
