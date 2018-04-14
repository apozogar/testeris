package com.everis.testeris.web.rest;

import com.everis.testeris.TesterisApp;

import com.everis.testeris.domain.Caso;
import com.everis.testeris.repository.CasoRepository;
import com.everis.testeris.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everis.testeris.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.everis.testeris.domain.enumeration.Estado;
/**
 * Test class for the CasoResource REST controller.
 *
 * @see CasoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesterisApp.class)
public class CasoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.OK;
    private static final Estado UPDATED_ESTADO = Estado.KO;

    @Autowired
    private CasoRepository casoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCasoMockMvc;

    private Caso caso;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CasoResource casoResource = new CasoResource(casoRepository);
        this.restCasoMockMvc = MockMvcBuilders.standaloneSetup(casoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caso createEntity(EntityManager em) {
        Caso caso = new Caso()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .estado(DEFAULT_ESTADO);
        return caso;
    }

    @Before
    public void initTest() {
        caso = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaso() throws Exception {
        int databaseSizeBeforeCreate = casoRepository.findAll().size();

        // Create the Caso
        restCasoMockMvc.perform(post("/api/casos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caso)))
            .andExpect(status().isCreated());

        // Validate the Caso in the database
        List<Caso> casoList = casoRepository.findAll();
        assertThat(casoList).hasSize(databaseSizeBeforeCreate + 1);
        Caso testCaso = casoList.get(casoList.size() - 1);
        assertThat(testCaso.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCaso.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCaso.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createCasoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = casoRepository.findAll().size();

        // Create the Caso with an existing ID
        caso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCasoMockMvc.perform(post("/api/casos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caso)))
            .andExpect(status().isBadRequest());

        // Validate the Caso in the database
        List<Caso> casoList = casoRepository.findAll();
        assertThat(casoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCasos() throws Exception {
        // Initialize the database
        casoRepository.saveAndFlush(caso);

        // Get all the casoList
        restCasoMockMvc.perform(get("/api/casos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    @Transactional
    public void getCaso() throws Exception {
        // Initialize the database
        casoRepository.saveAndFlush(caso);

        // Get the caso
        restCasoMockMvc.perform(get("/api/casos/{id}", caso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caso.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCaso() throws Exception {
        // Get the caso
        restCasoMockMvc.perform(get("/api/casos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaso() throws Exception {
        // Initialize the database
        casoRepository.saveAndFlush(caso);
        int databaseSizeBeforeUpdate = casoRepository.findAll().size();

        // Update the caso
        Caso updatedCaso = casoRepository.findOne(caso.getId());
        // Disconnect from session so that the updates on updatedCaso are not directly saved in db
        em.detach(updatedCaso);
        updatedCaso
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .estado(UPDATED_ESTADO);

        restCasoMockMvc.perform(put("/api/casos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCaso)))
            .andExpect(status().isOk());

        // Validate the Caso in the database
        List<Caso> casoList = casoRepository.findAll();
        assertThat(casoList).hasSize(databaseSizeBeforeUpdate);
        Caso testCaso = casoList.get(casoList.size() - 1);
        assertThat(testCaso.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCaso.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCaso.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingCaso() throws Exception {
        int databaseSizeBeforeUpdate = casoRepository.findAll().size();

        // Create the Caso

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCasoMockMvc.perform(put("/api/casos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caso)))
            .andExpect(status().isCreated());

        // Validate the Caso in the database
        List<Caso> casoList = casoRepository.findAll();
        assertThat(casoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCaso() throws Exception {
        // Initialize the database
        casoRepository.saveAndFlush(caso);
        int databaseSizeBeforeDelete = casoRepository.findAll().size();

        // Get the caso
        restCasoMockMvc.perform(delete("/api/casos/{id}", caso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Caso> casoList = casoRepository.findAll();
        assertThat(casoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caso.class);
        Caso caso1 = new Caso();
        caso1.setId(1L);
        Caso caso2 = new Caso();
        caso2.setId(caso1.getId());
        assertThat(caso1).isEqualTo(caso2);
        caso2.setId(2L);
        assertThat(caso1).isNotEqualTo(caso2);
        caso1.setId(null);
        assertThat(caso1).isNotEqualTo(caso2);
    }
}
