package com.bancopichincha.inventario.service.client;

import feign.Logger;
import feign.Retryer;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductoFeignConfiguration {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Retryer retry() {
        return new ProductoRetryer();
    }

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

}
