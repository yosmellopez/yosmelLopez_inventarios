package com.bancopichincha.inventario.service;

import com.bancopichincha.inventario.domain.Tienda;
import com.bancopichincha.inventario.repository.TiendaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tienda}.
 */
@Service
@Transactional
public class TiendaService {

    private final Logger log = LoggerFactory.getLogger(TiendaService.class);

    private final TiendaRepository tiendaRepository;

    public TiendaService(TiendaRepository tiendaRepository) {
        this.tiendaRepository = tiendaRepository;
    }

    /**
     * Save a tienda.
     *
     * @param tienda the entity to save.
     * @return the persisted entity.
     */
    public Tienda save(Tienda tienda) {
        log.debug("Request to save Tienda : {}", tienda);
        return tiendaRepository.save(tienda);
    }

    /**
     * Update a tienda.
     *
     * @param tienda the entity to save.
     * @return the persisted entity.
     */
    public Tienda update(Tienda tienda) {
        log.debug("Request to save Tienda : {}", tienda);
        return tiendaRepository.save(tienda);
    }

    /**
     * Partially update a tienda.
     *
     * @param tienda the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Tienda> partialUpdate(Tienda tienda) {
        log.debug("Request to partially update Tienda : {}", tienda);

        return tiendaRepository
            .findById(tienda.getId())
            .map(existingTienda -> {
                if (tienda.getNombre() != null) {
                    existingTienda.setNombre(tienda.getNombre());
                }
                if (tienda.getCodigo() != null) {
                    existingTienda.setCodigo(tienda.getCodigo());
                }

                return existingTienda;
            })
            .map(tiendaRepository::save);
    }

    /**
     * Get all the tiendas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Tienda> findAll() {
        log.debug("Request to get all Tiendas");
        return tiendaRepository.findAll();
    }

    /**
     * Get one tienda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Tienda> findOne(Long id) {
        log.debug("Request to get Tienda : {}", id);
        return tiendaRepository.findById(id);
    }

    /**
     * Delete the tienda by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tienda : {}", id);
        tiendaRepository.deleteById(id);
    }
}
