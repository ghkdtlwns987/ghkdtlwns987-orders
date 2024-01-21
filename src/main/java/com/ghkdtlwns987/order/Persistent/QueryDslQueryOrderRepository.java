package com.ghkdtlwns987.order.Persistent;

import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Repository.QueryOrderRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class QueryDslQueryOrderRepository implements QueryOrderRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public boolean existsOrderByProductId(String phone){
        QOrder member = Qorder.member;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(member)
                .where(member.phone.eq(phone))
                .fetchFirst()).isPresent();
    }

    @Override
    public Optional<Order> findByProductId(String productId) {
        QOrder qOrder
    }
}
