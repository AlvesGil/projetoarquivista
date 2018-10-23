package br.ufpa.arquivista.web.rest;

import br.ufpa.arquivista.ArquivistaApp;

import br.ufpa.arquivista.domain.Comprovante;
import br.ufpa.arquivista.repository.ComprovanteRepository;
import br.ufpa.arquivista.web.rest.errors.ExceptionTranslator;

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


import static br.ufpa.arquivista.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ComprovanteResource REST controller.
 *
 * @see ComprovanteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArquivistaApp.class)
public class ComprovanteResourceIntTest {

    private static final String DEFAULT_MES = "AAAAAAAAAA";
    private static final String UPDATED_MES = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANO = 1;
    private static final Integer UPDATED_ANO = 2;

    @Autowired
    private ComprovanteRepository comprovanteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComprovanteMockMvc;

    private Comprovante comprovante;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComprovanteResource comprovanteResource = new ComprovanteResource(comprovanteRepository);
        this.restComprovanteMockMvc = MockMvcBuilders.standaloneSetup(comprovanteResource)
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
    public static Comprovante createEntity(EntityManager em) {
        Comprovante comprovante = new Comprovante()
            .mes(DEFAULT_MES)
            .ano(DEFAULT_ANO);
        return comprovante;
    }

    @Before
    public void initTest() {
        comprovante = createEntity(em);
    }

    @Test
    @Transactional
    public void createComprovante() throws Exception {
        int databaseSizeBeforeCreate = comprovanteRepository.findAll().size();

        // Create the Comprovante
        restComprovanteMockMvc.perform(post("/api/comprovantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comprovante)))
            .andExpect(status().isCreated());

        // Validate the Comprovante in the database
        List<Comprovante> comprovanteList = comprovanteRepository.findAll();
        assertThat(comprovanteList).hasSize(databaseSizeBeforeCreate + 1);
        Comprovante testComprovante = comprovanteList.get(comprovanteList.size() - 1);
        assertThat(testComprovante.getMes()).isEqualTo(DEFAULT_MES);
        assertThat(testComprovante.getAno()).isEqualTo(DEFAULT_ANO);
    }

    @Test
    @Transactional
    public void createComprovanteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comprovanteRepository.findAll().size();

        // Create the Comprovante with an existing ID
        comprovante.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComprovanteMockMvc.perform(post("/api/comprovantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comprovante)))
            .andExpect(status().isBadRequest());

        // Validate the Comprovante in the database
        List<Comprovante> comprovanteList = comprovanteRepository.findAll();
        assertThat(comprovanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComprovantes() throws Exception {
        // Initialize the database
        comprovanteRepository.saveAndFlush(comprovante);

        // Get all the comprovanteList
        restComprovanteMockMvc.perform(get("/api/comprovantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comprovante.getId().intValue())))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES.toString())))
            .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO)));
    }
    
    @Test
    @Transactional
    public void getComprovante() throws Exception {
        // Initialize the database
        comprovanteRepository.saveAndFlush(comprovante);

        // Get the comprovante
        restComprovanteMockMvc.perform(get("/api/comprovantes/{id}", comprovante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comprovante.getId().intValue()))
            .andExpect(jsonPath("$.mes").value(DEFAULT_MES.toString()))
            .andExpect(jsonPath("$.ano").value(DEFAULT_ANO));
    }

    @Test
    @Transactional
    public void getNonExistingComprovante() throws Exception {
        // Get the comprovante
        restComprovanteMockMvc.perform(get("/api/comprovantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComprovante() throws Exception {
        // Initialize the database
        comprovanteRepository.saveAndFlush(comprovante);

        int databaseSizeBeforeUpdate = comprovanteRepository.findAll().size();

        // Update the comprovante
        Comprovante updatedComprovante = comprovanteRepository.findById(comprovante.getId()).get();
        // Disconnect from session so that the updates on updatedComprovante are not directly saved in db
        em.detach(updatedComprovante);
        updatedComprovante
            .mes(UPDATED_MES)
            .ano(UPDATED_ANO);

        restComprovanteMockMvc.perform(put("/api/comprovantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComprovante)))
            .andExpect(status().isOk());

        // Validate the Comprovante in the database
        List<Comprovante> comprovanteList = comprovanteRepository.findAll();
        assertThat(comprovanteList).hasSize(databaseSizeBeforeUpdate);
        Comprovante testComprovante = comprovanteList.get(comprovanteList.size() - 1);
        assertThat(testComprovante.getMes()).isEqualTo(UPDATED_MES);
        assertThat(testComprovante.getAno()).isEqualTo(UPDATED_ANO);
    }

    @Test
    @Transactional
    public void updateNonExistingComprovante() throws Exception {
        int databaseSizeBeforeUpdate = comprovanteRepository.findAll().size();

        // Create the Comprovante

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComprovanteMockMvc.perform(put("/api/comprovantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comprovante)))
            .andExpect(status().isBadRequest());

        // Validate the Comprovante in the database
        List<Comprovante> comprovanteList = comprovanteRepository.findAll();
        assertThat(comprovanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComprovante() throws Exception {
        // Initialize the database
        comprovanteRepository.saveAndFlush(comprovante);

        int databaseSizeBeforeDelete = comprovanteRepository.findAll().size();

        // Get the comprovante
        restComprovanteMockMvc.perform(delete("/api/comprovantes/{id}", comprovante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Comprovante> comprovanteList = comprovanteRepository.findAll();
        assertThat(comprovanteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comprovante.class);
        Comprovante comprovante1 = new Comprovante();
        comprovante1.setId(1L);
        Comprovante comprovante2 = new Comprovante();
        comprovante2.setId(comprovante1.getId());
        assertThat(comprovante1).isEqualTo(comprovante2);
        comprovante2.setId(2L);
        assertThat(comprovante1).isNotEqualTo(comprovante2);
        comprovante1.setId(null);
        assertThat(comprovante1).isNotEqualTo(comprovante2);
    }
}
