package com.ghkdtlwns987.order.Catalog.Service.Impl;

import com.ghkdtlwns987.order.Catalog.Dto.RequestOrderForCatalogDto;
import com.ghkdtlwns987.order.Catalog.Dto.ResponseOrderForCatalogDto;
import com.ghkdtlwns987.order.Catalog.Rest.Query.QueryCatalog;
import com.ghkdtlwns987.order.Catalog.Service.Inter.QueryCatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryCatalogServiceImpl implements QueryCatalogService {
    private final QueryCatalog queryCatalog;
    @Override
    public ResponseOrderForCatalogDto queryCatalogByProductId(RequestOrderForCatalogDto requestCatalogDto) throws ServerException {
        return queryCatalog.getCategoriesByProductIdRequest(requestCatalogDto.getProductId());
    }
}
