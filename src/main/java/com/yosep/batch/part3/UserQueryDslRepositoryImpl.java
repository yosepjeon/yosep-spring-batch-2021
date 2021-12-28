package com.yosep.batch.part3;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yosep.batch.project1.QUser;
import org.springframework.stereotype.Repository;

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
}
