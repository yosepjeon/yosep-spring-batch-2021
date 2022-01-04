package com.yosep.batch.project1;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserQueryDslRepositoryImpl implements UserQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public UserQueryDslRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public long findMinId() {
        return jpaQueryFactory.select(QUser.user.id.min())
                .from(QUser.user)
                .fetchOne();
    }

    @Override
    public long findMaxId() {
        return jpaQueryFactory.select(QUser.user.id.max())
                .from(QUser.user)
                .fetchOne();
    }

    @Override
    public List<User> findAllByUpdatedDate(LocalDateTime updatedDate) {
        return jpaQueryFactory.select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.updatedDate.before(updatedDate))
                .fetch();
    }
}
