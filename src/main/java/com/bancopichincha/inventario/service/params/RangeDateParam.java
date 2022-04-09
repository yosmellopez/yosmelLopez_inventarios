package com.bancopichincha.inventario.service.params;

import com.bancopichincha.inventario.web.rest.validator.annotation.DateMaxToday;
import com.bancopichincha.inventario.web.rest.validator.annotation.DateMinNineteenFifty;
import java.time.LocalDate;

public class RangeDateParam {

    @DateMinNineteenFifty
    private LocalDate startDate;

    @DateMaxToday
    private LocalDate endDate;

    public RangeDateParam(LocalDate start, LocalDate end) {
        this.startDate = start;
        this.endDate = end;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
