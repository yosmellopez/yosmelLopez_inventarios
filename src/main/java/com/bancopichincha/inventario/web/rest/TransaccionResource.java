package com.bancopichincha.inventario.web.rest;

import com.bancopichincha.inventario.domain.Transaccion;
import com.bancopichincha.inventario.repository.TransaccionRepository;
import com.bancopichincha.inventario.service.CsvExportService;
import com.bancopichincha.inventario.service.dto.TransaccionFecha;
import com.bancopichincha.inventario.service.dto.TransaccionMonto;
import com.bancopichincha.inventario.service.params.RangeDateParam;
import com.bancopichincha.inventario.web.rest.errors.BadRequestAlertException;
import com.bancopichincha.inventario.web.rest.validator.annotation.DateRange;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bancopichincha.inventario.domain.Transaccion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransaccionResource {

    private final Logger log = LoggerFactory.getLogger(TransaccionResource.class);

    private static final String ENTITY_NAME = "transaccion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransaccionRepository transaccionRepository;

    private final CsvExportService csvExportService;

    public TransaccionResource(TransaccionRepository transaccionRepository, CsvExportService csvExportService) {
        this.transaccionRepository = transaccionRepository;
        this.csvExportService = csvExportService;
    }

    /**
     * {@code POST  /transaccions} : Create a new transaccion.
     *
     * @param transaccion the transaccion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transaccion, or with status {@code 400 (Bad Request)} if the transaccion has already an ID.
     */
    @PostMapping("/transaccions")
    public ResponseEntity<Transaccion> createTransaccion(@Valid @RequestBody Transaccion transaccion) {
        log.debug("REST request to save Transaccion : {}", transaccion);
        if (transaccion.getId() != null) {
            throw new BadRequestAlertException("A new transaccion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transaccion result = transaccionRepository.save(transaccion);
        return ResponseEntity
                .created(URI.create("/api/transaccions/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


    /**
     * {@code GET  /transaccions} : get all the transaccions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transaccions in body.
     */
    @GetMapping("/transaccions")
    public List<TransaccionFecha> getAllTransaccions() {
        log.debug("REST request to get all Transaccions");
        return transaccionRepository.findTransaccionGroupByFecha();
    }

    /**
     * {@code GET  /transaccions} : get all the transaccions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transaccions in body.
     */
    @GetMapping("/transaccions/montos")
    public List<TransaccionMonto> getAllTransaccionsMontos() {
        log.debug("REST request to get all Transaccions");
        return transaccionRepository.findTransaccionGroupByMontos();
    }

    /**
     * {@code GET  /transaccions/:id} : get the "id" transaccion.
     *
     * @param id the id of the transaccion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transaccion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaccions/{id}")
    public ResponseEntity<Transaccion> getTransaccion(@PathVariable Long id) {
        log.debug("REST request to get Transaccion : {}", id);
        Optional<Transaccion> transaccion = transaccionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transaccion);
    }

    @GetMapping(path = "/transaccions/reporte")
    public void getAllEmployeesInCsv(HttpServletResponse servletResponse, @Valid @DateRange RangeDateParam dateParam, @RequestParam Long clientId) {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"transacciones.csv\"");
        csvExportService.writeTransaccionesToCsv(servletResponse, dateParam, clientId);
    }

    /**
     * {@code DELETE  /transaccions/:id} : delete the "id" transaccion.
     *
     * @param id the id of the transaccion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaccions/{id}")
    public ResponseEntity<Void> deleteTransaccion(@PathVariable Long id) {
        log.debug("REST request to delete Transaccion : {}", id);
        transaccionRepository.deleteById(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
