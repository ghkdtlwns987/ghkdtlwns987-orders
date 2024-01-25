package com.ghkdtlwns987.order.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Repository.CommandOrderRepository;
import com.ghkdtlwns987.order.Repository.QueryOrderRepository;
import com.ghkdtlwns987.order.Service.Inter.CommandOrderService;
import com.ghkdtlwns987.order.Service.Inter.QueryOrderService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QueryOrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class QueryOrderControllerTest {

    @MockBean
    private CommandOrderService commandOrderService;

    @Mock
    private QueryOrderRepository queryOrderRepository;
    @MockBean
    private QueryOrderService queryOrderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String userId1 = "c291c70c-0a00-4165-a665-a3fb657f08b1";
    private final String productId1 = "CATALOG-0001";
    private final String productId2 = "CATALOG-0002";
    private final String productId3 = "CATALOG-0003";
    private final Integer qty = 3;
    private final Integer unitPrice = 1000;

    private OrderRequestDto orderRequestDto;
    private Order order1;
    private Order order2;
    private Order order3;

    private OrderResponseDto responseDto1;
    private OrderResponseDto responseDto2;
    private OrderResponseDto responseDto3;
    List<OrderResponseDto> responses;

    private String orderId = UUID.randomUUID().toString();
    private String userId = UUID.randomUUID().toString();
    @BeforeEach
    void setUp() {
        queryOrderRepository = Mockito.mock(QueryOrderRepository.class);
        orderRequestDto = OrderRequestDto.builder()
                .productId(productId1)
                .qty(qty)
                .unitPrice(unitPrice)
                .build();

        order1 = Order.builder()
                .productId(productId1)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(3000)
                .orderId(orderId)
                .userId(userId)
                .build();

        order2 = Order.builder()
                .productId(productId2)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(3000)
                .orderId(orderId)
                .userId(userId)
                .build();

        order3 = Order.builder()
                .productId(productId2)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(3000)
                .orderId(orderId)
                .userId(userId)
                .build();

        responseDto1 = OrderResponseDto.fromEntity(order1);
        responseDto2 = OrderResponseDto.fromEntity(order2);
        responseDto3 = OrderResponseDto.fromEntity(order3);
        responses = Arrays.asList(responseDto1, responseDto2, responseDto3);
    }
    @Test
    void 주문_생성요청_테스트_존재하지_않는_userId로_빈배열_반환() throws Exception {
        // given
        final String invalidUserId = "invalid-userId-string";
        List<OrderResponseDto> responses = new ArrayList<>();

        queryOrderService.orderExistsByProductId(productId1);
        queryOrderService.getOrderByUserId(userId);
        when(queryOrderService.orderExistsByProductId(any())).thenReturn(false);
        when(queryOrderService.getOrderByUserId(any())).thenReturn(responses);

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/order/" + invalidUserId + "/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequestDto))
        );

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("O001")))
                .andExpect(jsonPath("$.message", equalTo("주문 내역을 조회했습니다.")))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(0)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void 주문_생성요청_테스트() throws Exception {
        // given
        queryOrderService.orderExistsByProductId(productId1);
        queryOrderService.getOrderByUserId(userId);
        queryOrderService.getOrderByUserId(userId);
        when(queryOrderService.orderExistsByProductId(any())).thenReturn(false);
        when(queryOrderService.getOrderByUserId(any())).thenReturn(responses);

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/order/" + userId1 + "/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequestDto))
        );

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("O001")))
                .andExpect(jsonPath("$.message", equalTo("주문 내역을 조회했습니다.")))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(3)))

                .andExpect(jsonPath("$.data[0].productId", equalTo(responses.get(0).getProductId())))
                .andExpect(jsonPath("$.data[0].qty", equalTo(responses.get(0).getQty())))
                .andExpect(jsonPath("$.data[0].unitPrice", equalTo(responses.get(0).getUnitPrice())))
                .andExpect(jsonPath("$.data[0].totalPrice", equalTo(responses.get(0).getTotalPrice())))
                .andExpect(jsonPath("$.data[0].userId", equalTo(responses.get(0).getUserId())))
                .andExpect(jsonPath("$.data[0].orderId", equalTo(responses.get(0).getOrderId())))

                .andExpect(jsonPath("$.data[1].productId", equalTo(responses.get(1).getProductId())))
                .andExpect(jsonPath("$.data[1].qty", equalTo(responses.get(1).getQty())))
                .andExpect(jsonPath("$.data[1].unitPrice", equalTo(responses.get(1).getUnitPrice())))
                .andExpect(jsonPath("$.data[1].totalPrice", equalTo(responses.get(1).getTotalPrice())))
                .andExpect(jsonPath("$.data[1].userId", equalTo(responses.get(1).getUserId())))
                .andExpect(jsonPath("$.data[1].orderId", equalTo(responses.get(1).getOrderId())))

                .andExpect(jsonPath("$.data[2].productId", equalTo(responses.get(2).getProductId())))
                .andExpect(jsonPath("$.data[2].qty", equalTo(responses.get(2).getQty())))
                .andExpect(jsonPath("$.data[2].unitPrice", equalTo(responses.get(2).getUnitPrice())))
                .andExpect(jsonPath("$.data[2].totalPrice", equalTo(responses.get(2).getTotalPrice())))
                .andExpect(jsonPath("$.data[2].userId", equalTo(responses.get(2).getUserId())))
                .andExpect(jsonPath("$.data[2].orderId", equalTo(responses.get(2).getOrderId())))

                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
