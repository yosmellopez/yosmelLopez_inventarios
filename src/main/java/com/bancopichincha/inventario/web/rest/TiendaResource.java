package com.bancopichincha.inventario.web.rest;

import com.bancopichincha.inventario.domain.Tienda;
import com.bancopichincha.inventario.repository.TiendaRepository;
import com.bancopichincha.inventario.service.TiendaService;
import com.bancopichincha.inventario.service.dto.TiendaProductoDTO;
import com.bancopichincha.inventario.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bancopichincha.inventario.domain.Tienda}.
 */
@RestController
@RequestMapping("/api")
public class TiendaResource {

    private final Logger log = LoggerFactory.getLogger(TiendaResource.class);

    private static final String ENTITY_NAME = "tienda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TiendaService tiendaService;

    private final TiendaRepository tiendaRepository;

    public TiendaResource(TiendaService tiendaService, TiendaRepository tiendaRepository) {
        this.tiendaService = tiendaService;
        this.tiendaRepository = tiendaRepository;
    }

    /**
     * {@code POST  /tiendas} : Create a new tienda.
     *
     * @param tienda the tienda to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tienda, or with status {@code 400 (Bad Request)} if the tienda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tiendas")
    public ResponseEntity<Tienda> createTienda(@Valid @RequestBody Tienda tienda) throws URISyntaxException {
        log.debug("REST request to save Tienda : {}", tienda);
        if (tienda.getId() != null) {
            throw new BadRequestAlertException("A new tienda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tienda result = tiendaService.save(tienda);
        return ResponseEntity
                .created(URI.create("/api/tiendas/" + result.getId())).headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /tiendas/:id} : Updates an existing tienda.
     *
     * @param id the id of the tienda to save.
     * @param tienda the tienda to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tienda,
     * or with status {@code 400 (Bad Request)} if the tienda is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tienda couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tiendas/{id}")
    public ResponseEntity<Tienda> updateTienda(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Tienda tienda) throws URISyntaxException {
        log.debug("REST request to update Tienda : {}, {}", id, tienda);
        if (tienda.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tienda.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tiendaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Tienda result = tiendaService.update(tienda);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tienda.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /tiendas/:id} : Updates an existing tiendaProductoDTO.
     *
     * @param id the id of the tiendaProductoDTO to save.
     * @param tiendaProducto the tiendaProductoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tiendaProductoDTO,
     * or with status {@code 400 (Bad Request)} if the tiendaProductoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tiendaProductoDTO couldn't be updated.
     */
    @PutMapping("/tiendas/{id}/productos")
    public ResponseEntity<TiendaProductoDTO> addProductoTienda(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody TiendaProductoDTO tiendaProducto) {
        log.debug("REST request to update Tienda : {}, {}", id, tiendaProducto);

        if (!tiendaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TiendaProductoDTO result = tiendaService.addProductosTienda(id, tiendaProducto);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /tiendas/:id} : Partial updates given fields of an existing tienda, field will ignore if it is null
     *
     * @param id the id of the tienda to save.
     * @param tienda the tienda to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tienda,
     * or with status {@code 400 (Bad Request)} if the tienda is not valid,
     * or with status {@code 404 (Not Found)} if the tienda is not found,
     * or with status {@code 500 (Internal Server Error)} if the tienda couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tiendas/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<Tienda> partialUpdateTienda(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Tienda tienda) throws URISyntaxException {
        log.debug("REST request to partial update Tienda partially : {}, {}", id, tienda);
        if (tienda.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tienda.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tiendaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tienda> result = tiendaService.partialUpdate(tienda);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tienda.getId().toString()));
    }

    /**
     * {@code GET  /tiendas} : get all the tiendas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tiendas in body.
     */
    @GetMapping("/tiendas")
    public List<Tienda> getAllTiendas() {
        log.debug("REST request to get all Tiendas");
        return tiendaService.findAll();
    }

    /**
     * {@code GET  /tiendas/:id} : get the "id" tienda.
     *
     * @param id the id of the tienda to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tienda, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tiendas/{id}")
    public ResponseEntity<Tienda> getTienda(@PathVariable Long id) {
        log.debug("REST request to get Tienda : {}", id);
        Optional<Tienda> tienda = tiendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tienda);
    }

    /**
     * {@code DELETE  /tiendas/:id} : delete the "id" tienda.
     *
     * @param id the id of the tienda to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tiendas/{id}")
    public ResponseEntity<Void> deleteTienda(@PathVariable Long id) {
        log.debug("REST request to delete Tienda : {}", id);
        tiendaService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
