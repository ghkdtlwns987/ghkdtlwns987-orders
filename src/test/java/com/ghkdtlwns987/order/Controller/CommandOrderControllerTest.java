package com.ghkdtlwns987.order.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Exception.Class.ProductIdAlreadyExistsException;
import com.ghkdtlwns987.order.Repository.CommandOrderRepository;
import com.ghkdtlwns987.order.Repository.QueryOrderRepository;
import com.ghkdtlwns987.order.Service.Inter.CommandOrderService;
import com.ghkdtlwns987.order.Service.Inter.QueryOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommandOrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommandOrderControllerTest {

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

    @Mock
    private CommandOrderRepository commandOrderRepository;

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
    private String orderId = UUID.randomUUID().toString();
    private String userId = UUID.randomUUID().toString();
    @BeforeEach
    void setUp() {
        commandOrderRepository = Mockito.mock(CommandOrderRepository.class);
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
    }

    @Test
    void 주문_생성요청_실패_productId가_이미_존재함() throws Exception{
        OrderRequestDto request = OrderRequestDto.builder()
                .productId(productId1)
                .unitPrice(unitPrice)
                .qty(qty)
                .build();

        when(queryOrderService.orderExistsByProductId(any())).thenReturn(true);
        when(commandOrderService.createOrder(any(), any(OrderRequestDto.class))).thenThrow(new ProductIdAlreadyExistsException());


        ResultActions perform = mockMvc.perform(post("/api/v1/order/" + userId1 + "/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data", equalTo(null)))
                .andExpect(jsonPath("$.errorMessage[0]", equalTo("PRODUCT ID IS ALREADY EXISTS")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandOrderService, times(1)).createOrder(any(), any(OrderRequestDto.class));
        verify(commandOrderRepository, never()).save(any(Order.class));
    }

    @Test
    void 주문_생성요청_테스트() throws Exception {
        // given
        commandOrderRepository.save(order1);
        OrderResponseDto orderResponseDto = OrderResponseDto.fromEntity(order1);

        when(queryOrderService.orderExistsByProductId(any())).thenReturn(false);
        when(commandOrderService.createOrder(any(), any(OrderRequestDto.class))).thenReturn(OrderResponseDto.fromEntity(order1));
        when(commandOrderRepository.save(any(Order.class))).thenReturn(order1);
        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/order/" + userId1 + "/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequestDto))
        );

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("O001")))
                .andExpect(jsonPath("$.message", equalTo("회원 주문이 접수되었습니다.")))
                .andExpect(jsonPath("$.data.productId", equalTo(orderResponseDto.getProductId())))
                .andExpect(jsonPath("$.data.qty", equalTo(orderResponseDto.getQty())))
                .andExpect(jsonPath("$.data.unitPrice", equalTo(orderResponseDto.getUnitPrice())))
                .andExpect(jsonPath("$.data.totalPrice", equalTo(orderResponseDto.getTotalPrice())))
                .andExpect(jsonPath("$.data.userId", equalTo(orderResponseDto.getUserId())))
                .andExpect(jsonPath("$.data.orderId", equalTo(orderResponseDto.getOrderId())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandOrderService, times(1)).createOrder(any(), any(OrderRequestDto.class));
        verify(commandOrderRepository, times(1)).save(any(Order.class));

    }
}
