package com.bancopichincha.inventario.service.client;

import com.bancopichincha.inventario.service.dto.ProductoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "productService",
        url = "https://api.mocki.io/v2/c65bab33",
        path = "products",
        configuration = ProductoFeignConfiguration.class)
public interface ProductServiceClient {

    @GetMapping(value = "/", produces = "application/json")
    ProductoResponse getProductsFromMockService();
}
