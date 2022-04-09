package com.bancopichincha.inventario.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class ListProductoResponse {

    @JsonProperty(value = "prods")
    private List<ProductoDTO> productos = new ArrayList<>();

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }
}
