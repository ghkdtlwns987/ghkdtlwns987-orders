package com.ghkdtlwns987.order.Repository;

import com.ghkdtlwns987.order.Entity.Order;

import java.util.Optional;

public interface QueryOrderRepository {
    /**
     * Id를 기반으로 회원을 찾는 기능입니다.
     * @param productId
     * @return Optional<Order>
     */
    Optional<Order> findOrderByProductId(String productId);

    /**
     * productId가 이미 존재하는지 찾는 기능입니다.
     * @param productId
     * @return boolean
     */
    boolean existsOrderByProductId(String productId);

    /**
     * userId 를 기준으로 회원이 주문한 상품이 있는지 검사합니다.
     * @param userId
     * @return boolean
     */
    boolean existsOrderByUserId(String userId);


    /**
     * userId 가 주문한 내역을 전부 조회합니다.
     * @param userId
     * @return Iterable<Order>
     */
    Iterable<Order> findOrderByUserId(String userId);

}
