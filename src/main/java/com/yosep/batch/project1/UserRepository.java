package com.yosep.batch.project1;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends UserQueryDslRepository, JpaRepository<User, Long> {
//    Collection<User> findAllByUpdatedDate(LocalDateTime update);
}
