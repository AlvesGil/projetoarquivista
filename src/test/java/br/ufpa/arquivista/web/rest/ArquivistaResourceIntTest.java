package br.ufpa.arquivista.web.rest;

import br.ufpa.arquivista.ArquivistaApp;

import br.ufpa.arquivista.domain.Arquivista;
import br.ufpa.arquivista.repository.ArquivistaRepository;
import br.ufpa.arquivista.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static br.ufpa.arquivista.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArquivistaResource REST controller.
 *
 * @see ArquivistaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArquivistaApp.class)
public class ArquivistaResourceIntTest {

    private static final String DEFAULT_CADLOCADOR = "AAAAAAAAAA";
    private static final String UPDATED_CADLOCADOR = "BBBBBBBBBB";

    private static final String DEFAULT_CADLOCATARIO = "AAAAAAAAAA";
    private static final String UPDATED_CADLOCATARIO = "BBBBBBBBBB";

    private static final String DEFAULT_CADIMOVEL = "AAAAAAAAAA";
    private static final String UPDATED_CADIMOVEL = "BBBBBBBBBB";

    @Autowired
    private ArquivistaRepository arquivistaRepository;

    @Mock
    private ArquivistaRepository arquivistaRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArquivistaMockMvc;

    private Arquivista arquivista;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArquivistaResource arquivistaResource = new ArquivistaResource(arquivistaRepository);
        this.restArquivistaMockMvc = MockMvcBuilders.standaloneSetup(arquivistaResource)
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
    public static Arquivista createEntity(EntityManager em) {
        Arquivista arquivista = new Arquivista()
            .cadlocador(DEFAULT_CADLOCADOR)
            .cadlocatario(DEFAULT_CADLOCATARIO)
            .cadimovel(DEFAULT_CADIMOVEL);
        return arquivista;
    }

    @Before
    public void initTest() {
        arquivista = createEntity(em);
    }

    @Test
    @Transactional
    public void createArquivista() throws Exception {
        int databaseSizeBeforeCreate = arquivistaRepository.findAll().size();

        // Create the Arquivista
        restArquivistaMockMvc.perform(post("/api/arquivistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivista)))
            .andExpect(status().isCreated());

        // Validate the Arquivista in the database
        List<Arquivista> arquivistaList = arquivistaRepository.findAll();
        assertThat(arquivistaList).hasSize(databaseSizeBeforeCreate + 1);
        Arquivista testArquivista = arquivistaList.get(arquivistaList.size() - 1);
        assertThat(testArquivista.getCadlocador()).isEqualTo(DEFAULT_CADLOCADOR);
        assertThat(testArquivista.getCadlocatario()).isEqualTo(DEFAULT_CADLOCATARIO);
        assertThat(testArquivista.getCadimovel()).isEqualTo(DEFAULT_CADIMOVEL);
    }

    @Test
    @Transactional
    public void createArquivistaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = arquivistaRepository.findAll().size();

        // Create the Arquivista with an existing ID
        arquivista.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArquivistaMockMvc.perform(post("/api/arquivistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivista)))
            .andExpect(status().isBadRequest());

        // Validate the Arquivista in the database
        List<Arquivista> arquivistaList = arquivistaRepository.findAll();
        assertThat(arquivistaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArquivistas() throws Exception {
        // Initialize the database
        arquivistaRepository.saveAndFlush(arquivista);

        // Get all the arquivistaList
        restArquivistaMockMvc.perform(get("/api/arquivistas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arquivista.getId().intValue())))
            .andExpect(jsonPath("$.[*].cadlocador").value(hasItem(DEFAULT_CADLOCADOR.toString())))
            .andExpect(jsonPath("$.[*].cadlocatario").value(hasItem(DEFAULT_CADLOCATARIO.toString())))
            .andExpect(jsonPath("$.[*].cadimovel").value(hasItem(DEFAULT_CADIMOVEL.toString())));
    }
    
    public void getAllArquivistasWithEagerRelationshipsIsEnabled() throws Exception {
        ArquivistaResource arquivistaResource = new ArquivistaResource(arquivistaRepositoryMock);
        when(arquivistaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restArquivistaMockMvc = MockMvcBuilders.standaloneSetup(arquivistaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restArquivistaMockMvc.perform(get("/api/arquivistas?eagerload=true"))
        .andExpect(status().isOk());

        verify(arquivistaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllArquivistasWithEagerRelationshipsIsNotEnabled() throws Exception {
        ArquivistaResource arquivistaResource = new ArquivistaResource(arquivistaRepositoryMock);
            when(arquivistaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restArquivistaMockMvc = MockMvcBuilders.standaloneSetup(arquivistaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restArquivistaMockMvc.perform(get("/api/arquivistas?eagerload=true"))
        .andExpect(status().isOk());

            verify(arquivistaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getArquivista() throws Exception {
        // Initialize the database
        arquivistaRepository.saveAndFlush(arquivista);

        // Get the arquivista
        restArquivistaMockMvc.perform(get("/api/arquivistas/{id}", arquivista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(arquivista.getId().intValue()))
            .andExpect(jsonPath("$.cadlocador").value(DEFAULT_CADLOCADOR.toString()))
            .andExpect(jsonPath("$.cadlocatario").value(DEFAULT_CADLOCATARIO.toString()))
            .andExpect(jsonPath("$.cadimovel").value(DEFAULT_CADIMOVEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArquivista() throws Exception {
        // Get the arquivista
        restArquivistaMockMvc.perform(get("/api/arquivistas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArquivista() throws Exception {
        // Initialize the database
        arquivistaRepository.saveAndFlush(arquivista);

        int databaseSizeBeforeUpdate = arquivistaRepository.findAll().size();

        // Update the arquivista
        Arquivista updatedArquivista = arquivistaRepository.findById(arquivista.getId()).get();
        // Disconnect from session so that the updates on updatedArquivista are not directly saved in db
        em.detach(updatedArquivista);
        updatedArquivista
            .cadlocador(UPDATED_CADLOCADOR)
            .cadlocatario(UPDATED_CADLOCATARIO)
            .cadimovel(UPDATED_CADIMOVEL);

        restArquivistaMockMvc.perform(put("/api/arquivistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArquivista)))
            .andExpect(status().isOk());

        // Validate the Arquivista in the database
        List<Arquivista> arquivistaList = arquivistaRepository.findAll();
        assertThat(arquivistaList).hasSize(databaseSizeBeforeUpdate);
        Arquivista testArquivista = arquivistaList.get(arquivistaList.size() - 1);
        assertThat(testArquivista.getCadlocador()).isEqualTo(UPDATED_CADLOCADOR);
        assertThat(testArquivista.getCadlocatario()).isEqualTo(UPDATED_CADLOCATARIO);
        assertThat(testArquivista.getCadimovel()).isEqualTo(UPDATED_CADIMOVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingArquivista() throws Exception {
        int databaseSizeBeforeUpdate = arquivistaRepository.findAll().size();

        // Create the Arquivista

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArquivistaMockMvc.perform(put("/api/arquivistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivista)))
            .andExpect(status().isBadRequest());

        // Validate the Arquivista in the database
        List<Arquivista> arquivistaList = arquivistaRepository.findAll();
        assertThat(arquivistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArquivista() throws Exception {
        // Initialize the database
        arquivistaRepository.saveAndFlush(arquivista);

        int databaseSizeBeforeDelete = arquivistaRepository.findAll().size();

        // Get the arquivista
        restArquivistaMockMvc.perform(delete("/api/arquivistas/{id}", arquivista.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Arquivista> arquivistaList = arquivistaRepository.findAll();
        assertThat(arquivistaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Arquivista.class);
        Arquivista arquivista1 = new Arquivista();
        arquivista1.setId(1L);
        Arquivista arquivista2 = new Arquivista();
        arquivista2.setId(arquivista1.getId());
        assertThat(arquivista1).isEqualTo(arquivista2);
        arquivista2.setId(2L);
        assertThat(arquivista1).isNotEqualTo(arquivista2);
        arquivista1.setId(null);
        assertThat(arquivista1).isNotEqualTo(arquivista2);
    }
}
