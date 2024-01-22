package com.ghkdtlwns987.order.Persistent;

import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Entity.QOrder;
import com.ghkdtlwns987.order.Repository.QueryOrderRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class QueryDslQueryOrderRepository implements QueryOrderRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public boolean existsOrderByProductId(String productId){
        QOrder qOrder = QOrder.order;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(qOrder)
                .where(qOrder.productId.eq(productId))
                .fetchFirst()).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsOrderByUserId(String userId) {
        QOrder qOrder = QOrder.order;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(qOrder)
                .where(qOrder.userId.eq(qOrder.userId))
                .fetchFirst()).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findOrderByUserId(String userId) {
        QOrder qOrder = QOrder.order;
        return jpaQueryFactory.selectFrom(qOrder)
                .where(qOrder.userId.eq(userId))
                .fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findOrderByProductId(String productId) {
        QOrder qOrder = QOrder.order;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(qOrder)
                .where(qOrder.productId.eq(productId))
                .fetchFirst());
    }
}
