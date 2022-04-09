package com.bancopichincha.inventario.service.dto;

import com.bancopichincha.inventario.domain.Producto;
import com.bancopichincha.inventario.domain.Transaccion;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public interface TransaccionInfo {

    Long getId();

    LocalDate getFecha();

    Instant getHora();

    Integer getCantidad();

    TiendaInfo getTienda();

    ProductoInfo getProducto();

    interface TiendaInfo {

        Long getId();

        String getNombre();

        String getCodigo();

        Set<Producto> getProductos();
    }

    interface ProductoInfo {

        Long getId();

        String getCod();

        String getName();

        Double getPrice();

        Long getStock();

        Set<Transaccion> getTransaccions();
    }
}
