package com.yosep.batch.part3;

import com.yosep.batch.project1.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface UserRepository extends UserQueryDslRepository, JpaRepository<User, Long> {
    Collection<User> findAllByUpdateDate(LocalDate update);
}
