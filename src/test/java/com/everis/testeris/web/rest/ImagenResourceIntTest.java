package com.everis.testeris.web.rest;

import com.everis.testeris.TesterisApp;

import com.everis.testeris.domain.Imagen;
import com.everis.testeris.repository.ImagenRepository;
import com.everis.testeris.service.ImagenService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everis.testeris.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ImagenResource REST controller.
 *
 * @see ImagenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesterisApp.class)
public class ImagenResourceIntTest {

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ImagenRepository imagenRepository;

    @Autowired
    private ImagenService imagenService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImagenMockMvc;

    private Imagen imagen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImagenResource imagenResource = new ImagenResource(imagenService);
        this.restImagenMockMvc = MockMvcBuilders.standaloneSetup(imagenResource)
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
    public static Imagen createEntity(EntityManager em) {
        Imagen imagen = new Imagen()
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .nombre(DEFAULT_NOMBRE)
            .description(DEFAULT_DESCRIPTION);
        return imagen;
    }

    @Before
    public void initTest() {
        imagen = createEntity(em);
    }

    @Test
    @Transactional
    public void createImagen() throws Exception {
        int databaseSizeBeforeCreate = imagenRepository.findAll().size();

        // Create the Imagen
        restImagenMockMvc.perform(post("/api/imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagen)))
            .andExpect(status().isCreated());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeCreate + 1);
        Imagen testImagen = imagenList.get(imagenList.size() - 1);
        assertThat(testImagen.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testImagen.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testImagen.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testImagen.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createImagenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imagenRepository.findAll().size();

        // Create the Imagen with an existing ID
        imagen.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImagenMockMvc.perform(post("/api/imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagen)))
            .andExpect(status().isBadRequest());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImagens() throws Exception {
        // Initialize the database
        imagenRepository.saveAndFlush(imagen);

        // Get all the imagenList
        restImagenMockMvc.perform(get("/api/imagens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imagen.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getImagen() throws Exception {
        // Initialize the database
        imagenRepository.saveAndFlush(imagen);

        // Get the imagen
        restImagenMockMvc.perform(get("/api/imagens/{id}", imagen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imagen.getId().intValue()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImagen() throws Exception {
        // Get the imagen
        restImagenMockMvc.perform(get("/api/imagens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImagen() throws Exception {
        // Initialize the database
        imagenService.save(imagen);

        int databaseSizeBeforeUpdate = imagenRepository.findAll().size();

        // Update the imagen
        Imagen updatedImagen = imagenRepository.findOne(imagen.getId());
        // Disconnect from session so that the updates on updatedImagen are not directly saved in db
        em.detach(updatedImagen);
        updatedImagen
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .nombre(UPDATED_NOMBRE)
            .description(UPDATED_DESCRIPTION);

        restImagenMockMvc.perform(put("/api/imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImagen)))
            .andExpect(status().isOk());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeUpdate);
        Imagen testImagen = imagenList.get(imagenList.size() - 1);
        assertThat(testImagen.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testImagen.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testImagen.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testImagen.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingImagen() throws Exception {
        int databaseSizeBeforeUpdate = imagenRepository.findAll().size();

        // Create the Imagen

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImagenMockMvc.perform(put("/api/imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagen)))
            .andExpect(status().isCreated());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteImagen() throws Exception {
        // Initialize the database
        imagenService.save(imagen);

        int databaseSizeBeforeDelete = imagenRepository.findAll().size();

        // Get the imagen
        restImagenMockMvc.perform(delete("/api/imagens/{id}", imagen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Imagen.class);
        Imagen imagen1 = new Imagen();
        imagen1.setId(1L);
        Imagen imagen2 = new Imagen();
        imagen2.setId(imagen1.getId());
        assertThat(imagen1).isEqualTo(imagen2);
        imagen2.setId(2L);
        assertThat(imagen1).isNotEqualTo(imagen2);
        imagen1.setId(null);
        assertThat(imagen1).isNotEqualTo(imagen2);
    }
}
