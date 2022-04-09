package com.bancopichincha.inventario.service.dto;

import com.bancopichincha.inventario.domain.Tienda;
import java.time.LocalDate;

public class TransaccionFecha {

    private LocalDate fecha;

    private Tienda tienda;

    private Long transacciones;

    public TransaccionFecha(LocalDate fecha, Tienda tienda, Long transacciones) {
        this.fecha = fecha;
        this.tienda = tienda;
        this.transacciones = transacciones;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(Long transacciones) {
        this.transacciones = transacciones;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }
}
