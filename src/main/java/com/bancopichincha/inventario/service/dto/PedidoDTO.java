package com.bancopichincha.inventario.service.dto;

import com.bancopichincha.inventario.domain.Cliente;
import java.util.ArrayList;
import java.util.List;

public class PedidoDTO {

    private Cliente cliente;

    private List<PedidoProducto> productos = new ArrayList<>();

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<PedidoProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<PedidoProducto> productos) {
        this.productos = productos;
    }
}
