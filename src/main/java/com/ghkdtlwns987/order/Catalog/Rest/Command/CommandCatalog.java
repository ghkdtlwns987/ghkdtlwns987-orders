package com.ghkdtlwns987.order.Catalog.Rest.Command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.order.Catalog.Config.CatalogConfig;
import com.ghkdtlwns987.order.Catalog.Dto.RequestOrderForCatalogDto;
import com.ghkdtlwns987.order.Catalog.Dto.ResponseOrderForCatalogDto;
import com.ghkdtlwns987.order.Exception.ClientException;
import com.ghkdtlwns987.order.Exception.ErrorCode;
import com.ghkdtlwns987.order.Global.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.rmi.ServerException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandCatalog {
    private final CatalogConfig catalogConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    public ResponseOrderForCatalogDto createOrderRequestForCatalog(RequestOrderForCatalogDto request) throws ServerException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RequestOrderForCatalogDto> requestEntity = new HttpEntity<>(request, headers);

        URI uri = UriComponentsBuilder
                .fromUriString(catalogConfig.getCatalogUrl())
                .path("/catalog/orders")
                .build()
                .toUri();

        log.info("uri: " + uri);


        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
            String jsonResponse = responseEntity.getBody();
            ResultResponse resultResponse = objectMapper.readValue(
                    jsonResponse,
                    ResultResponse.class
            );

            return Optional.ofNullable(resultResponse.getData())
                    .map(d -> objectMapper.convertValue(d, ResponseOrderForCatalogDto.class))
                    .orElseThrow(() -> new RuntimeException("Failed to map JSON response"));
        } catch (HttpClientErrorException e){
            log.error("", e);

            if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                if(e.getMessage().contains(ErrorCode.PRODUCT_ID_NOT_EXISTS.getMessage())){
                    throw new ClientException(ErrorCode.PRODUCT_ID_NOT_EXISTS, ErrorCode.PRODUCT_ID_NOT_EXISTS.getMessage());
                }
                if(e.getMessage().contains(ErrorCode.OUT_OF_STOCK.getMessage())){
                    throw new ClientException(ErrorCode.OUT_OF_STOCK, ErrorCode.OUT_OF_STOCK.getMessage());
                }
            }
            throw new ServerException(
                    ErrorCode.INTERNAL_SERVER_ERROR.getCode()
            );
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
