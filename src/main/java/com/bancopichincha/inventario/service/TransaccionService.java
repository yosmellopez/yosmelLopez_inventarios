package com.bancopichincha.inventario.service;

import com.bancopichincha.inventario.domain.Producto;
import com.bancopichincha.inventario.domain.Tienda;
import com.bancopichincha.inventario.domain.Transaccion;
import com.bancopichincha.inventario.repository.ProductoRepository;
import com.bancopichincha.inventario.repository.TiendaRepository;
import com.bancopichincha.inventario.repository.TransaccionRepository;
import com.bancopichincha.inventario.service.dto.PedidoDTO;
import com.bancopichincha.inventario.service.dto.PedidoProducto;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Transaccion}.
 */
@Service
@Transactional
public class TransaccionService {

    private final Logger log = LoggerFactory.getLogger(TransaccionService.class);

    private final TransaccionRepository transaccionRepository;

    private final ProductoRepository productoRepository;

    private final TiendaRepository tiendaRepository;

    public TransaccionService(TransaccionRepository transaccionRepository, ProductoRepository productoRepository, TiendaRepository tiendaRepository) {
        this.transaccionRepository = transaccionRepository;
        this.productoRepository = productoRepository;
        this.tiendaRepository = tiendaRepository;
    }

    /**
     * Save a transaccion.
     *
     * @param transaccion the entity to save.
     * @return the persisted entity.
     */
    public Transaccion save(Transaccion transaccion) {
        log.debug("Request to save Transaccion : {}", transaccion);
        return transaccionRepository.save(transaccion);
    }

    /**
     * Update a transaccion.
     *
     * @param transaccion the entity to save.
     * @return the persisted entity.
     */
    public Transaccion update(Transaccion transaccion) {
        log.debug("Request to save Transaccion : {}", transaccion);
        return transaccionRepository.save(transaccion);
    }

    /**
     * Get all the transaccions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Transaccion> findAll() {
        log.debug("Request to get all Transaccions");
        return transaccionRepository.findAll();
    }

    /**
     * Get one transaccion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Transaccion> findOne(Long id) {
        log.debug("Request to get Transaccion : {}", id);
        return transaccionRepository.findById(id);
    }

    /**
     * Delete the transaccion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Transaccion : {}", id);
        transaccionRepository.deleteById(id);
    }

    public void createPedido(PedidoDTO pedido) {
        List<PedidoProducto> productos = pedido.getProductos();
        for (PedidoProducto pedidoProducto : productos) {
            Transaccion transaccion = new Transaccion();
            Optional<Producto> optionalProducto = productoRepository.findById(pedidoProducto.getProductoId());
            Producto productoBd = optionalProducto.orElseThrow(() -> new EntityNotFoundException("No se encuentra el pedidoProducto con el identificador " + pedidoProducto.getProductoId()));
            transaccion.setProducto(productoBd);
            optionalProducto.ifPresent(transaccion::setProducto);
            Optional<Tienda> optionalTienda = tiendaRepository.findById(pedidoProducto.getTiendaId());
            optionalTienda.ifPresent(transaccion::setTienda);
            transaccion.setFecha(LocalDate.now());
            transaccion.setCliente(pedido.getCliente());
            transaccion.setHora(Instant.now());
            transaccion.setCantidad(pedidoProducto.getCantidad());
            transaccionRepository.save(transaccion);
            productoBd.setStock(productoBd.getStock() - pedidoProducto.getCantidad());
            productoRepository.save(productoBd);
        }
    }
}
