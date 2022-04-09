package com.bancopichincha.inventario.web.rest;

import com.bancopichincha.inventario.domain.Tienda;
import com.bancopichincha.inventario.service.TiendaService;
import com.bancopichincha.inventario.service.TransaccionService;
import com.bancopichincha.inventario.service.dto.PedidoDTO;
import com.bancopichincha.inventario.web.rest.validator.PedidoProductoValidator;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link Tienda}.
 */
@RestController
@RequestMapping("/api")
public class PedidoResource {

    private final Logger log = LoggerFactory.getLogger(PedidoResource.class);

    private final TransaccionService transaccionService;

    private final PedidoProductoValidator validator;

    public PedidoResource(TransaccionService transaccionService, PedidoProductoValidator validator) {
        this.transaccionService = transaccionService;
        this.validator = validator;
    }

    /**
     * {@code POST  /tiendas} : Create a new pedido.
     *
     * @param pedido the pedido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pedido, or with status {@code 400 (Bad Request)} if the pedido has already an ID.
     */
    @PostMapping("/pedidos")
    public ResponseEntity<Void> createPedidoCliente(@Valid @RequestBody PedidoDTO pedido) {
        log.debug("REST request to save Tienda : {}", pedido);
        transaccionService.createPedido(pedido);
        return ResponseEntity
                .noContent()
                .build();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        Optional.ofNullable(binder.getTarget()).ifPresent(target -> {
            if (validator.supports(target.getClass())) {
                binder.addValidators(validator);
            }
        });
    }
}
