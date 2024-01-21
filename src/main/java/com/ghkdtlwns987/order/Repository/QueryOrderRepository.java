package com.ghkdtlwns987.order.Repository;

import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.order.Entity.Order;

import java.util.Optional;

public interface QueryOrderRepository {
    /**
     * Id를 기반으로 회원을 찾는 기능입니다.
     * @param productId
     * @return Optional<Order>
     */
    Optional<Order> findByProductId(String productId);

    /**
     * productId가 이미 존재하는지 찾는 기능입니다.
     * @param productId
     * @return boolean
     */
    boolean existsOrderByProductId(String productId);
}
