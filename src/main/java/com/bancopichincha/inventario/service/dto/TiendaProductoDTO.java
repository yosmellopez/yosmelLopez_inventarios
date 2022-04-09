package com.bancopichincha.inventario.service.dto;

import com.bancopichincha.inventario.domain.Tienda;
import java.util.ArrayList;
import java.util.List;

public class TiendaProductoDTO {

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
