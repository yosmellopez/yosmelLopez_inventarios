package com.bancopichincha.inventario.web.rest.errors;

import java.net.URI;
import javax.annotation.Nullable;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ResourceNotFoundException extends AbstractThrowableProblem {

    private final String error;

    public ResourceNotFoundException(@Nullable URI type, @Nullable String title, @Nullable String detail) {
        super(type, title, Status.NOT_FOUND, detail);
        this.error = detail;
    }

    public String getError() {
        return error;
    }
}
