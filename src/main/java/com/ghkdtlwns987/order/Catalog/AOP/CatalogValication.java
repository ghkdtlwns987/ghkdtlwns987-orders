package com.ghkdtlwns987.order.Catalog.AOP;

import com.ghkdtlwns987.order.Catalog.Rest.Query.QueryCatalog;
import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class CatalogValication {
    private final QueryCatalog queryCatalog;
}
