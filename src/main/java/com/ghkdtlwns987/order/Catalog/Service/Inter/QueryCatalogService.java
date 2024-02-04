package com.ghkdtlwns987.order.Catalog.Service.Inter;

import com.ghkdtlwns987.order.Catalog.Dto.RequestOrderForCatalogDto;
import com.ghkdtlwns987.order.Catalog.Dto.ResponseOrderForCatalogDto;

import java.rmi.ServerException;

public interface QueryCatalogService {

    /**
     * Catalog 서비스로부터 상품을 조회하는 기능입니다.
     * 해당 기능을 통해 유요한 주문 요청인지 확인합니다.
     * @param requestCatalogDto
     * @return ResponseOrderForCatalogDto
     * @author 황시준
     */
    ResponseOrderForCatalogDto queryCatalogByProductId(RequestOrderForCatalogDto requestCatalogDto) throws ServerException;
}
