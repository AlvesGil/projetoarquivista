package br.ufpa.arquivista.web.rest;

import br.ufpa.arquivista.ArquivistaApp;

import br.ufpa.arquivista.domain.Imovel;
import br.ufpa.arquivista.repository.ImovelRepository;
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
 * Test class for the ImovelResource REST controller.
 *
 * @see ImovelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArquivistaApp.class)
public class ImovelResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_END = "AAAAAAAAAA";
    private static final String UPDATED_END = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRATO = "AAAAAAAAAA";
    private static final String UPDATED_CONTRATO = "BBBBBBBBBB";

    private static final String DEFAULT_FICHA = "AAAAAAAAAA";
    private static final String UPDATED_FICHA = "BBBBBBBBBB";

    private static final String DEFAULT_OFICIOS = "AAAAAAAAAA";
    private static final String UPDATED_OFICIOS = "BBBBBBBBBB";

    private static final String DEFAULT_ADITIVO = "AAAAAAAAAA";
    private static final String UPDATED_ADITIVO = "BBBBBBBBBB";

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImovelMockMvc;

    private Imovel imovel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImovelResource imovelResource = new ImovelResource(imovelRepository);
        this.restImovelMockMvc = MockMvcBuilders.standaloneSetup(imovelResource)
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
    public static Imovel createEntity(EntityManager em) {
        Imovel imovel = new Imovel()
            .nome(DEFAULT_NOME)
            .end(DEFAULT_END)
            .contrato(DEFAULT_CONTRATO)
            .ficha(DEFAULT_FICHA)
            .oficios(DEFAULT_OFICIOS)
            .aditivo(DEFAULT_ADITIVO);
        return imovel;
    }

    @Before
    public void initTest() {
        imovel = createEntity(em);
    }

    @Test
    @Transactional
    public void createImovel() throws Exception {
        int databaseSizeBeforeCreate = imovelRepository.findAll().size();

        // Create the Imovel
        restImovelMockMvc.perform(post("/api/imovels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imovel)))
            .andExpect(status().isCreated());

        // Validate the Imovel in the database
        List<Imovel> imovelList = imovelRepository.findAll();
        assertThat(imovelList).hasSize(databaseSizeBeforeCreate + 1);
        Imovel testImovel = imovelList.get(imovelList.size() - 1);
        assertThat(testImovel.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testImovel.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testImovel.getContrato()).isEqualTo(DEFAULT_CONTRATO);
        assertThat(testImovel.getFicha()).isEqualTo(DEFAULT_FICHA);
        assertThat(testImovel.getOficios()).isEqualTo(DEFAULT_OFICIOS);
        assertThat(testImovel.getAditivo()).isEqualTo(DEFAULT_ADITIVO);
    }

    @Test
    @Transactional
    public void createImovelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imovelRepository.findAll().size();

        // Create the Imovel with an existing ID
        imovel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImovelMockMvc.perform(post("/api/imovels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imovel)))
            .andExpect(status().isBadRequest());

        // Validate the Imovel in the database
        List<Imovel> imovelList = imovelRepository.findAll();
        assertThat(imovelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImovels() throws Exception {
        // Initialize the database
        imovelRepository.saveAndFlush(imovel);

        // Get all the imovelList
        restImovelMockMvc.perform(get("/api/imovels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imovel.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].contrato").value(hasItem(DEFAULT_CONTRATO.toString())))
            .andExpect(jsonPath("$.[*].ficha").value(hasItem(DEFAULT_FICHA.toString())))
            .andExpect(jsonPath("$.[*].oficios").value(hasItem(DEFAULT_OFICIOS.toString())))
            .andExpect(jsonPath("$.[*].aditivo").value(hasItem(DEFAULT_ADITIVO.toString())));
    }
    
    @Test
    @Transactional
    public void getImovel() throws Exception {
        // Initialize the database
        imovelRepository.saveAndFlush(imovel);

        // Get the imovel
        restImovelMockMvc.perform(get("/api/imovels/{id}", imovel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imovel.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.contrato").value(DEFAULT_CONTRATO.toString()))
            .andExpect(jsonPath("$.ficha").value(DEFAULT_FICHA.toString()))
            .andExpect(jsonPath("$.oficios").value(DEFAULT_OFICIOS.toString()))
            .andExpect(jsonPath("$.aditivo").value(DEFAULT_ADITIVO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImovel() throws Exception {
        // Get the imovel
        restImovelMockMvc.perform(get("/api/imovels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImovel() throws Exception {
        // Initialize the database
        imovelRepository.saveAndFlush(imovel);

        int databaseSizeBeforeUpdate = imovelRepository.findAll().size();

        // Update the imovel
        Imovel updatedImovel = imovelRepository.findById(imovel.getId()).get();
        // Disconnect from session so that the updates on updatedImovel are not directly saved in db
        em.detach(updatedImovel);
        updatedImovel
            .nome(UPDATED_NOME)
            .end(UPDATED_END)
            .contrato(UPDATED_CONTRATO)
            .ficha(UPDATED_FICHA)
            .oficios(UPDATED_OFICIOS)
            .aditivo(UPDATED_ADITIVO);

        restImovelMockMvc.perform(put("/api/imovels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImovel)))
            .andExpect(status().isOk());

        // Validate the Imovel in the database
        List<Imovel> imovelList = imovelRepository.findAll();
        assertThat(imovelList).hasSize(databaseSizeBeforeUpdate);
        Imovel testImovel = imovelList.get(imovelList.size() - 1);
        assertThat(testImovel.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testImovel.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testImovel.getContrato()).isEqualTo(UPDATED_CONTRATO);
        assertThat(testImovel.getFicha()).isEqualTo(UPDATED_FICHA);
        assertThat(testImovel.getOficios()).isEqualTo(UPDATED_OFICIOS);
        assertThat(testImovel.getAditivo()).isEqualTo(UPDATED_ADITIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingImovel() throws Exception {
        int databaseSizeBeforeUpdate = imovelRepository.findAll().size();

        // Create the Imovel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImovelMockMvc.perform(put("/api/imovels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imovel)))
            .andExpect(status().isBadRequest());

        // Validate the Imovel in the database
        List<Imovel> imovelList = imovelRepository.findAll();
        assertThat(imovelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImovel() throws Exception {
        // Initialize the database
        imovelRepository.saveAndFlush(imovel);

        int databaseSizeBeforeDelete = imovelRepository.findAll().size();

        // Get the imovel
        restImovelMockMvc.perform(delete("/api/imovels/{id}", imovel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Imovel> imovelList = imovelRepository.findAll();
        assertThat(imovelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Imovel.class);
        Imovel imovel1 = new Imovel();
        imovel1.setId(1L);
        Imovel imovel2 = new Imovel();
        imovel2.setId(imovel1.getId());
        assertThat(imovel1).isEqualTo(imovel2);
        imovel2.setId(2L);
        assertThat(imovel1).isNotEqualTo(imovel2);
        imovel1.setId(null);
        assertThat(imovel1).isNotEqualTo(imovel2);
    }
}
