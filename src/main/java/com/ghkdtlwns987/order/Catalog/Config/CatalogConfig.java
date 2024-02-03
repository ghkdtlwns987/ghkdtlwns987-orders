package com.ghkdtlwns987.order.Catalog.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CatalogConfig
{
    @Value("${catalog.url}")
    private String catalogUrl;
}
