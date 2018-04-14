package com.everis.testeris.web.rest;

import com.everis.testeris.TesterisApp;

import com.everis.testeris.domain.Paso;
import com.everis.testeris.repository.PasoRepository;
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
 * Test class for the PasoResource REST controller.
 *
 * @see PasoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesterisApp.class)
public class PasoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_PASO = "AAAAAAAAAA";
    private static final String UPDATED_NUM_PASO = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTADO_ESPERADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO_ESPERADO = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.OK;
    private static final Estado UPDATED_ESTADO = Estado.KO;

    @Autowired
    private PasoRepository pasoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPasoMockMvc;

    private Paso paso;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PasoResource pasoResource = new PasoResource(pasoRepository);
        this.restPasoMockMvc = MockMvcBuilders.standaloneSetup(pasoResource)
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
    public static Paso createEntity(EntityManager em) {
        Paso paso = new Paso()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .numPaso(DEFAULT_NUM_PASO)
            .resultadoEsperado(DEFAULT_RESULTADO_ESPERADO)
            .estado(DEFAULT_ESTADO);
        return paso;
    }

    @Before
    public void initTest() {
        paso = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaso() throws Exception {
        int databaseSizeBeforeCreate = pasoRepository.findAll().size();

        // Create the Paso
        restPasoMockMvc.perform(post("/api/pasos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paso)))
            .andExpect(status().isCreated());

        // Validate the Paso in the database
        List<Paso> pasoList = pasoRepository.findAll();
        assertThat(pasoList).hasSize(databaseSizeBeforeCreate + 1);
        Paso testPaso = pasoList.get(pasoList.size() - 1);
        assertThat(testPaso.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPaso.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPaso.getNumPaso()).isEqualTo(DEFAULT_NUM_PASO);
        assertThat(testPaso.getResultadoEsperado()).isEqualTo(DEFAULT_RESULTADO_ESPERADO);
        assertThat(testPaso.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createPasoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pasoRepository.findAll().size();

        // Create the Paso with an existing ID
        paso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPasoMockMvc.perform(post("/api/pasos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paso)))
            .andExpect(status().isBadRequest());

        // Validate the Paso in the database
        List<Paso> pasoList = pasoRepository.findAll();
        assertThat(pasoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPasos() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get all the pasoList
        restPasoMockMvc.perform(get("/api/pasos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].numPaso").value(hasItem(DEFAULT_NUM_PASO.toString())))
            .andExpect(jsonPath("$.[*].resultadoEsperado").value(hasItem(DEFAULT_RESULTADO_ESPERADO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    @Transactional
    public void getPaso() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);

        // Get the paso
        restPasoMockMvc.perform(get("/api/pasos/{id}", paso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paso.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.numPaso").value(DEFAULT_NUM_PASO.toString()))
            .andExpect(jsonPath("$.resultadoEsperado").value(DEFAULT_RESULTADO_ESPERADO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaso() throws Exception {
        // Get the paso
        restPasoMockMvc.perform(get("/api/pasos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaso() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);
        int databaseSizeBeforeUpdate = pasoRepository.findAll().size();

        // Update the paso
        Paso updatedPaso = pasoRepository.findOne(paso.getId());
        // Disconnect from session so that the updates on updatedPaso are not directly saved in db
        em.detach(updatedPaso);
        updatedPaso
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .numPaso(UPDATED_NUM_PASO)
            .resultadoEsperado(UPDATED_RESULTADO_ESPERADO)
            .estado(UPDATED_ESTADO);

        restPasoMockMvc.perform(put("/api/pasos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaso)))
            .andExpect(status().isOk());

        // Validate the Paso in the database
        List<Paso> pasoList = pasoRepository.findAll();
        assertThat(pasoList).hasSize(databaseSizeBeforeUpdate);
        Paso testPaso = pasoList.get(pasoList.size() - 1);
        assertThat(testPaso.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPaso.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPaso.getNumPaso()).isEqualTo(UPDATED_NUM_PASO);
        assertThat(testPaso.getResultadoEsperado()).isEqualTo(UPDATED_RESULTADO_ESPERADO);
        assertThat(testPaso.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingPaso() throws Exception {
        int databaseSizeBeforeUpdate = pasoRepository.findAll().size();

        // Create the Paso

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPasoMockMvc.perform(put("/api/pasos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paso)))
            .andExpect(status().isCreated());

        // Validate the Paso in the database
        List<Paso> pasoList = pasoRepository.findAll();
        assertThat(pasoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaso() throws Exception {
        // Initialize the database
        pasoRepository.saveAndFlush(paso);
        int databaseSizeBeforeDelete = pasoRepository.findAll().size();

        // Get the paso
        restPasoMockMvc.perform(delete("/api/pasos/{id}", paso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Paso> pasoList = pasoRepository.findAll();
        assertThat(pasoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paso.class);
        Paso paso1 = new Paso();
        paso1.setId(1L);
        Paso paso2 = new Paso();
        paso2.setId(paso1.getId());
        assertThat(paso1).isEqualTo(paso2);
        paso2.setId(2L);
        assertThat(paso1).isNotEqualTo(paso2);
        paso1.setId(null);
        assertThat(paso1).isNotEqualTo(paso2);
    }
}
