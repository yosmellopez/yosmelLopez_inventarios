package com.bancopichincha.inventario.service.dto;

import com.bancopichincha.inventario.domain.Tienda;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

public class TiendaProductoDTO {

    @JsonIgnoreProperties(value = {"productos"})
    private Tienda tienda;

    private List<ProductoDTO> productos = new ArrayList<>();

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }
}
