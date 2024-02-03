package com.ghkdtlwns987.order.Catalog.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalogDto {
    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;
}