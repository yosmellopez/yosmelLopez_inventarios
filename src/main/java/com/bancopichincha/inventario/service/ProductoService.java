package com.bancopichincha.inventario.service;

import com.bancopichincha.inventario.domain.Producto;
import com.bancopichincha.inventario.repository.ProductoRepository;
import com.bancopichincha.inventario.service.client.ProductServiceClient;
import com.bancopichincha.inventario.service.dto.ProductoDTO;
import com.bancopichincha.inventario.service.dto.ProductoResponse;
import com.bancopichincha.inventario.service.mapper.ProductoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Producto}.
 */
@Service
@Transactional
public class ProductoService {

    private final Logger log = LoggerFactory.getLogger(ProductoService.class);

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    private final ProductServiceClient serviceClient;

    public ProductoService(ProductoRepository productoRepository, ProductoMapper productoMapper, ProductServiceClient serviceClient) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.serviceClient = serviceClient;
    }

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductoDTO save(ProductoDTO productoDTO) {
        log.debug("Request to save Producto : {}", productoDTO);
        Producto producto = productoMapper.toEntity(productoDTO);
        producto = productoRepository.save(producto);
        return productoMapper.toDto(producto);
    }

    /**
     * Update a producto.
     *
     * @param productoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductoDTO update(ProductoDTO productoDTO) {
        log.debug("Request to save Producto : {}", productoDTO);
        Producto producto = productoMapper.toEntity(productoDTO);
        producto = productoRepository.save(producto);
        return productoMapper.toDto(producto);
    }

    /**
     * Partially update a producto.
     *
     * @param productoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductoDTO> partialUpdate(ProductoDTO productoDTO) {
        log.debug("Request to partially update Producto : {}", productoDTO);

        return productoRepository
                .findById(productoDTO.getId())
                .map(existingProducto -> {
                    productoMapper.partialUpdate(existingProducto, productoDTO);

                    return existingProducto;
                })
                .map(productoRepository::save)
                .map(productoMapper::toDto);
    }

    /**
     * Get all the productos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProductoDTO> findAll() {
        log.debug("Request to get all Productos");
        return productoRepository.findAll().stream().map(productoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one producto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> findOne(Long id) {
        log.debug("Request to get Producto : {}", id);
        return productoRepository.findById(id).map(productoMapper::toDto);
    }

    /**
     * Delete the producto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Producto : {}", id);
        productoRepository.deleteById(id);
    }

    public void updateProductStockInTen(Producto producto) {
        ProductoResponse productoResponse = serviceClient.getTenProductsFromMockService();
        Long stock = producto.getStock();
        producto.setStock(stock + productoResponse.getStock());
        productoRepository.save(producto);
    }

    @Async
    public void updateProductStockInFive(Producto producto) {
        ProductoResponse productoResponse = serviceClient.getFiveProductFromMockService();
        Long stock = producto.getStock();
        producto.setStock(stock + productoResponse.getStock());
        productoRepository.saveAndFlush(producto);
    }
}
