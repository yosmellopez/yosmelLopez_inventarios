package com.bancopichincha.inventario.web.rest.validator;

import com.bancopichincha.inventario.domain.Producto;
import com.bancopichincha.inventario.repository.ProductoRepository;
import com.bancopichincha.inventario.service.ProductoService;
import com.bancopichincha.inventario.service.dto.PedidoDTO;
import com.bancopichincha.inventario.service.dto.PedidoProducto;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PedidoProductoValidator implements Validator {

    private final ProductoRepository productoRepository;

    private final ProductoService productoService;

    @Autowired
    public PedidoProductoValidator(ProductoRepository productoRepository, ProductoService productoService) {
        this.productoRepository = productoRepository;
        this.productoService = productoService;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return PedidoDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Optional<PedidoDTO> dtoOptional = Optional.of(target).filter(PedidoDTO.class::isInstance).map(PedidoDTO.class::cast);
        dtoOptional.ifPresent(pedido -> {
            List<PedidoProducto> productos = pedido.getProductos();
            for (PedidoProducto pedidoProducto : productos) {
                Optional<Producto> optionalProducto = productoRepository.findById(pedidoProducto.getProductoId());
                optionalProducto.ifPresentOrElse(producto -> {
                    Long stock = producto.getStock();
                    long restante = stock - pedidoProducto.getCantidad();
                    if (restante < -5 && restante >= -10) {
                        productoService.updateProductStockInTen(producto);
                    } else if (restante < 0 && restante >= -5) {
                        productoService.updateProductStockInFive(producto);
                    } else if (restante < -10) {
                        errors.reject("productos", "Unidades no disponibles (> 10) ");
                    }
                }, () -> errors.reject("productos", "El producto con identificador " + pedidoProducto.getProductoId() + " no existe"));
            }
        });
    }
}
