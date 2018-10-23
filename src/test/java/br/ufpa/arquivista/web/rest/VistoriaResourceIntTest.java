package br.ufpa.arquivista.web.rest;

import br.ufpa.arquivista.ArquivistaApp;

import br.ufpa.arquivista.domain.Vistoria;
import br.ufpa.arquivista.repository.VistoriaRepository;
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
 * Test class for the VistoriaResource REST controller.
 *
 * @see VistoriaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArquivistaApp.class)
public class VistoriaResourceIntTest {

    private static final String DEFAULT_DOCUMENTOS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTOS = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGENS = "AAAAAAAAAA";
    private static final String UPDATED_IMAGENS = "BBBBBBBBBB";

    @Autowired
    private VistoriaRepository vistoriaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVistoriaMockMvc;

    private Vistoria vistoria;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VistoriaResource vistoriaResource = new VistoriaResource(vistoriaRepository);
        this.restVistoriaMockMvc = MockMvcBuilders.standaloneSetup(vistoriaResource)
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
    public static Vistoria createEntity(EntityManager em) {
        Vistoria vistoria = new Vistoria()
            .documentos(DEFAULT_DOCUMENTOS)
            .imagens(DEFAULT_IMAGENS);
        return vistoria;
    }

    @Before
    public void initTest() {
        vistoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createVistoria() throws Exception {
        int databaseSizeBeforeCreate = vistoriaRepository.findAll().size();

        // Create the Vistoria
        restVistoriaMockMvc.perform(post("/api/vistorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vistoria)))
            .andExpect(status().isCreated());

        // Validate the Vistoria in the database
        List<Vistoria> vistoriaList = vistoriaRepository.findAll();
        assertThat(vistoriaList).hasSize(databaseSizeBeforeCreate + 1);
        Vistoria testVistoria = vistoriaList.get(vistoriaList.size() - 1);
        assertThat(testVistoria.getDocumentos()).isEqualTo(DEFAULT_DOCUMENTOS);
        assertThat(testVistoria.getImagens()).isEqualTo(DEFAULT_IMAGENS);
    }

    @Test
    @Transactional
    public void createVistoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vistoriaRepository.findAll().size();

        // Create the Vistoria with an existing ID
        vistoria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVistoriaMockMvc.perform(post("/api/vistorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vistoria)))
            .andExpect(status().isBadRequest());

        // Validate the Vistoria in the database
        List<Vistoria> vistoriaList = vistoriaRepository.findAll();
        assertThat(vistoriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVistorias() throws Exception {
        // Initialize the database
        vistoriaRepository.saveAndFlush(vistoria);

        // Get all the vistoriaList
        restVistoriaMockMvc.perform(get("/api/vistorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vistoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentos").value(hasItem(DEFAULT_DOCUMENTOS.toString())))
            .andExpect(jsonPath("$.[*].imagens").value(hasItem(DEFAULT_IMAGENS.toString())));
    }
    
    @Test
    @Transactional
    public void getVistoria() throws Exception {
        // Initialize the database
        vistoriaRepository.saveAndFlush(vistoria);

        // Get the vistoria
        restVistoriaMockMvc.perform(get("/api/vistorias/{id}", vistoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vistoria.getId().intValue()))
            .andExpect(jsonPath("$.documentos").value(DEFAULT_DOCUMENTOS.toString()))
            .andExpect(jsonPath("$.imagens").value(DEFAULT_IMAGENS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVistoria() throws Exception {
        // Get the vistoria
        restVistoriaMockMvc.perform(get("/api/vistorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVistoria() throws Exception {
        // Initialize the database
        vistoriaRepository.saveAndFlush(vistoria);

        int databaseSizeBeforeUpdate = vistoriaRepository.findAll().size();

        // Update the vistoria
        Vistoria updatedVistoria = vistoriaRepository.findById(vistoria.getId()).get();
        // Disconnect from session so that the updates on updatedVistoria are not directly saved in db
        em.detach(updatedVistoria);
        updatedVistoria
            .documentos(UPDATED_DOCUMENTOS)
            .imagens(UPDATED_IMAGENS);

        restVistoriaMockMvc.perform(put("/api/vistorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVistoria)))
            .andExpect(status().isOk());

        // Validate the Vistoria in the database
        List<Vistoria> vistoriaList = vistoriaRepository.findAll();
        assertThat(vistoriaList).hasSize(databaseSizeBeforeUpdate);
        Vistoria testVistoria = vistoriaList.get(vistoriaList.size() - 1);
        assertThat(testVistoria.getDocumentos()).isEqualTo(UPDATED_DOCUMENTOS);
        assertThat(testVistoria.getImagens()).isEqualTo(UPDATED_IMAGENS);
    }

    @Test
    @Transactional
    public void updateNonExistingVistoria() throws Exception {
        int databaseSizeBeforeUpdate = vistoriaRepository.findAll().size();

        // Create the Vistoria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVistoriaMockMvc.perform(put("/api/vistorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vistoria)))
            .andExpect(status().isBadRequest());

        // Validate the Vistoria in the database
        List<Vistoria> vistoriaList = vistoriaRepository.findAll();
        assertThat(vistoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVistoria() throws Exception {
        // Initialize the database
        vistoriaRepository.saveAndFlush(vistoria);

        int databaseSizeBeforeDelete = vistoriaRepository.findAll().size();

        // Get the vistoria
        restVistoriaMockMvc.perform(delete("/api/vistorias/{id}", vistoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vistoria> vistoriaList = vistoriaRepository.findAll();
        assertThat(vistoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vistoria.class);
        Vistoria vistoria1 = new Vistoria();
        vistoria1.setId(1L);
        Vistoria vistoria2 = new Vistoria();
        vistoria2.setId(vistoria1.getId());
        assertThat(vistoria1).isEqualTo(vistoria2);
        vistoria2.setId(2L);
        assertThat(vistoria1).isNotEqualTo(vistoria2);
        vistoria1.setId(null);
        assertThat(vistoria1).isNotEqualTo(vistoria2);
    }
}
