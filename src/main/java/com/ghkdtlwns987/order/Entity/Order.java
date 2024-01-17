package com.ghkdtlwns987.order.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "productId", nullable = false, unique = true)
    private String productId;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "unitPrice", nullable = false)
    private Integer unitPrice;

    @Column(name = "totalPrice", nullable = false)
    private Integer totalPrice;

    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(name = "orderId", nullable = false)
    private String orderId;

    @Column(name = "orderedAt", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime orderAt;

    @Builder
    public Order(Long Id, String productId, Integer qty, Integer unitPrice, Integer totalPrice, String userId, String orderId){
        this.Id = Id;
        this.productId = productId;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.orderId = orderId;
    }
}
