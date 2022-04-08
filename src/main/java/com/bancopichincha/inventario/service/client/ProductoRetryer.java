package com.bancopichincha.inventario.service.client;

import com.bancopichincha.inventario.web.rest.errors.ResourceNotFoundException;
import feign.RetryableException;
import feign.Retryer;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductoRetryer implements Retryer {

    private final Logger log = LoggerFactory.getLogger(ProductoRetryer.class);

    private final int maxAttempts;

    int attempt;

    public ProductoRetryer() {
        this(3, 0);
    }

    public ProductoRetryer(int maxAttempts, int attempt) {
        this.maxAttempts = maxAttempts;
        this.attempt = attempt;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        log.error("Hubo un error en el cliente al intentar conectarse");
        if (this.attempt++ >= this.maxAttempts) {
            throw new ResourceNotFoundException(URI.create("/coinpayment"), "Error Time Out", "global.error.serverNotReachable");
        }
    }

    @Override
    public Retryer clone() {
        return new ProductoRetryer(maxAttempts, attempt);
    }
}
