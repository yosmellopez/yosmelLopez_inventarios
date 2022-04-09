package com.bancopichincha.inventario.web.rest.validator;

import com.bancopichincha.inventario.domain.Producto;
import com.bancopichincha.inventario.domain.Tienda;
import com.bancopichincha.inventario.repository.ProductoRepository;
import com.bancopichincha.inventario.repository.TiendaRepository;
import com.bancopichincha.inventario.service.ProductoService;
import com.bancopichincha.inventario.service.dto.PedidoDTO;
import com.bancopichincha.inventario.service.dto.PedidoProducto;
import com.bancopichincha.inventario.web.rest.errors.ResourceNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PedidoProductoValidator implements Validator {

    private final ProductoRepository productoRepository;

    private final TiendaRepository tiendaRepository;

    private final ProductoService productoService;

    @Autowired
    public PedidoProductoValidator(ProductoRepository productoRepository, TiendaRepository tiendaRepository, ProductoService productoService) {
        this.productoRepository = productoRepository;
        this.tiendaRepository = tiendaRepository;
        this.productoService = productoService;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return PedidoDTO.class.equals(clazz);
    }

    @Override
    @Transactional
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Optional<PedidoDTO> dtoOptional = Optional.of(target).filter(PedidoDTO.class::isInstance).map(PedidoDTO.class::cast);
        dtoOptional.ifPresent(pedido -> {
            List<PedidoProducto> productos = pedido.getProductos();
            for (PedidoProducto pedidoProducto : productos) {
                Optional<Tienda> optionalTienda = tiendaRepository.findById(pedidoProducto.getTiendaId());
                Tienda tienda = optionalTienda.orElseThrow(() -> new ResourceNotFoundException(URI.create("/api/pedidos"), "Tienda no encontrada", "No se encuentra la tienda con este identificador " + pedidoProducto.getTiendaId()));
                Optional<Producto> optionalProducto = productoRepository.findById(pedidoProducto.getProductoId());
                optionalProducto.ifPresentOrElse(producto -> {
                    Set<Producto> tiendaProductos = tienda.getProductos();
                    Optional<Producto> optional = tiendaProductos.stream().filter(p -> Objects.equals(p.getId(), producto.getId())).findAny();
                    if (optional.isEmpty()) {
                        errors.reject("productos", "La tienda " + tienda.getNombre() + " no vende el producto " + producto.getName());
                    } else {
                        Long stock = producto.getStock();
                        long restante = stock - pedidoProducto.getCantidad();
                        if (restante < -5 && restante >= -10) {
                            productoService.updateProductStockInTen(producto);
                        } else if (restante < -10) {
                            errors.reject("productos", "Unidades no disponibles (> 10)");
                        }
                    }
                }, () -> errors.reject("productos", "El producto con identificador " + pedidoProducto.getProductoId() + " no existe"));
            }
        });
    }
}
