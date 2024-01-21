package com.ghkdtlwns987.order.Service;

import com.ghkdtlwns987.order.Repository.CommandOrderRepository;
import com.ghkdtlwns987.order.Service.Impl.CommandOrderServiceImpl;
import com.ghkdtlwns987.order.Service.Impl.QueryOrderServiceImpl;
import com.ghkdtlwns987.order.Service.Inter.QueryOrderService;
import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@ExtendWith(MockitoExtension.class)
public class QueryOrderServiceTest {

    private CommandOrderServiceImpl commandOrderService;
    private QueryOrderServiceImpl queryOrderService;
    private CommandOrderRepository commandOrderRepository;
    @BeforeEach
    void setUp(){
        commandOrderRepository = Mockito.mock(CommandOrderRepository.class);
        queryOrderService = Mockito.mock(QueryOrderServiceImpl.class);

    }
}
