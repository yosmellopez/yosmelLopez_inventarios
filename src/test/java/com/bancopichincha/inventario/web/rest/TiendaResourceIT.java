package com.bancopichincha.inventario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bancopichincha.inventario.IntegrationTest;
import com.bancopichincha.inventario.domain.Tienda;
import com.bancopichincha.inventario.repository.TiendaRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TiendaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TiendaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tiendas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong count = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTiendaMockMvc;

    private Tienda tienda;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tienda createEntity(EntityManager em) {
        Tienda tienda = new Tienda().nombre(DEFAULT_NOMBRE).codigo(DEFAULT_CODIGO);
        return tienda;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tienda createUpdatedEntity(EntityManager em) {
        Tienda tienda = new Tienda().nombre(UPDATED_NOMBRE).codigo(UPDATED_CODIGO);
        return tienda;
    }

    @BeforeEach
    public void initTest() {
        tienda = createEntity(em);
    }

    @Test
    @Transactional
    void createTienda() throws Exception {
        int databaseSizeBeforeCreate = tiendaRepository.findAll().size();
        // Create the Tienda
        restTiendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tienda)))
            .andExpect(status().isCreated());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeCreate + 1);
        Tienda testTienda = tiendaList.get(tiendaList.size() - 1);
        assertThat(testTienda.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTienda.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    void createTiendaWithExistingId() throws Exception {
        // Create the Tienda with an existing ID
        tienda.setId(1L);

        int databaseSizeBeforeCreate = tiendaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTiendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tienda)))
            .andExpect(status().isBadRequest());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiendaRepository.findAll().size();
        // set the field null
        tienda.setNombre(null);

        // Create the Tienda, which fails.

        restTiendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tienda)))
            .andExpect(status().isBadRequest());

        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiendaRepository.findAll().size();
        // set the field null
        tienda.setCodigo(null);

        // Create the Tienda, which fails.

        restTiendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tienda)))
            .andExpect(status().isBadRequest());

        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTiendas() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get all the tiendaList
        restTiendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tienda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)));
    }

    @Test
    @Transactional
    void getTienda() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        // Get the tienda
        restTiendaMockMvc
            .perform(get(ENTITY_API_URL_ID, tienda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tienda.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO));
    }

    @Test
    @Transactional
    void getNonExistingTienda() throws Exception {
        // Get the tienda
        restTiendaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTienda() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        int databaseSizeBeforeUpdate = tiendaRepository.findAll().size();

        // Update the tienda
        Tienda updatedTienda = tiendaRepository.findById(tienda.getId()).get();
        // Disconnect from session so that the updates on updatedTienda are not directly saved in db
        em.detach(updatedTienda);
        updatedTienda.nombre(UPDATED_NOMBRE).codigo(UPDATED_CODIGO);

        restTiendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTienda.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTienda))
            )
            .andExpect(status().isOk());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeUpdate);
        Tienda testTienda = tiendaList.get(tiendaList.size() - 1);
        assertThat(testTienda.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTienda.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void putNonExistingTienda() throws Exception {
        int databaseSizeBeforeUpdate = tiendaRepository.findAll().size();
        tienda.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTiendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tienda.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tienda))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTienda() throws Exception {
        int databaseSizeBeforeUpdate = tiendaRepository.findAll().size();
        tienda.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTiendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tienda))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTienda() throws Exception {
        int databaseSizeBeforeUpdate = tiendaRepository.findAll().size();
        tienda.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTiendaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tienda)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTiendaWithPatch() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        int databaseSizeBeforeUpdate = tiendaRepository.findAll().size();

        // Update the tienda using partial update
        Tienda partialUpdatedTienda = new Tienda();
        partialUpdatedTienda.setId(tienda.getId());

        partialUpdatedTienda.nombre(UPDATED_NOMBRE).codigo(UPDATED_CODIGO);

        restTiendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTienda.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTienda))
            )
            .andExpect(status().isOk());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeUpdate);
        Tienda testTienda = tiendaList.get(tiendaList.size() - 1);
        assertThat(testTienda.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTienda.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void fullUpdateTiendaWithPatch() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        int databaseSizeBeforeUpdate = tiendaRepository.findAll().size();

        // Update the tienda using partial update
        Tienda partialUpdatedTienda = new Tienda();
        partialUpdatedTienda.setId(tienda.getId());

        partialUpdatedTienda.nombre(UPDATED_NOMBRE).codigo(UPDATED_CODIGO);

        restTiendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTienda.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTienda))
            )
            .andExpect(status().isOk());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeUpdate);
        Tienda testTienda = tiendaList.get(tiendaList.size() - 1);
        assertThat(testTienda.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTienda.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void patchNonExistingTienda() throws Exception {
        int databaseSizeBeforeUpdate = tiendaRepository.findAll().size();
        tienda.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTiendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tienda.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tienda))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTienda() throws Exception {
        int databaseSizeBeforeUpdate = tiendaRepository.findAll().size();
        tienda.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTiendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tienda))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTienda() throws Exception {
        int databaseSizeBeforeUpdate = tiendaRepository.findAll().size();
        tienda.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTiendaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tienda)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tienda in the database
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTienda() throws Exception {
        // Initialize the database
        tiendaRepository.saveAndFlush(tienda);

        int databaseSizeBeforeDelete = tiendaRepository.findAll().size();

        // Delete the tienda
        restTiendaMockMvc
            .perform(delete(ENTITY_API_URL_ID, tienda.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tienda> tiendaList = tiendaRepository.findAll();
        assertThat(tiendaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
