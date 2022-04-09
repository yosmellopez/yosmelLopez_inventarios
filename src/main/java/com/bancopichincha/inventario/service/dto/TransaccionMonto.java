package com.bancopichincha.inventario.service.dto;

import com.bancopichincha.inventario.domain.Producto;
import com.bancopichincha.inventario.domain.Tienda;
import java.math.BigDecimal;

public class TransaccionMonto {

    private Tienda tienda;

    private Producto producto;

    private Long cantidad;

    public TransaccionMonto(Tienda tienda, Producto producto, Long cantidad) {
        this.tienda = tienda;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotalVendido() {
        return BigDecimal.valueOf(producto.getPrice() * cantidad);
    }
}
