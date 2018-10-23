package br.ufpa.arquivista.web.rest;

import br.ufpa.arquivista.ArquivistaApp;

import br.ufpa.arquivista.domain.Locador;
import br.ufpa.arquivista.repository.LocadorRepository;
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
 * Test class for the LocadorResource REST controller.
 *
 * @see LocadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArquivistaApp.class)
public class LocadorResourceIntTest {

    private static final String DEFAULT_IMOVEL = "AAAAAAAAAA";
    private static final String UPDATED_IMOVEL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_END = "AAAAAAAAAA";
    private static final String UPDATED_END = "BBBBBBBBBB";

    private static final String DEFAULT_IDT = "AAAAAAAAAA";
    private static final String UPDATED_IDT = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    @Autowired
    private LocadorRepository locadorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocadorMockMvc;

    private Locador locador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocadorResource locadorResource = new LocadorResource(locadorRepository);
        this.restLocadorMockMvc = MockMvcBuilders.standaloneSetup(locadorResource)
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
    public static Locador createEntity(EntityManager em) {
        Locador locador = new Locador()
            .imovel(DEFAULT_IMOVEL)
            .nome(DEFAULT_NOME)
            .end(DEFAULT_END)
            .idt(DEFAULT_IDT)
            .cpf(DEFAULT_CPF);
        return locador;
    }

    @Before
    public void initTest() {
        locador = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocador() throws Exception {
        int databaseSizeBeforeCreate = locadorRepository.findAll().size();

        // Create the Locador
        restLocadorMockMvc.perform(post("/api/locadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locador)))
            .andExpect(status().isCreated());

        // Validate the Locador in the database
        List<Locador> locadorList = locadorRepository.findAll();
        assertThat(locadorList).hasSize(databaseSizeBeforeCreate + 1);
        Locador testLocador = locadorList.get(locadorList.size() - 1);
        assertThat(testLocador.getImovel()).isEqualTo(DEFAULT_IMOVEL);
        assertThat(testLocador.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testLocador.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testLocador.getIdt()).isEqualTo(DEFAULT_IDT);
        assertThat(testLocador.getCpf()).isEqualTo(DEFAULT_CPF);
    }

    @Test
    @Transactional
    public void createLocadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locadorRepository.findAll().size();

        // Create the Locador with an existing ID
        locador.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocadorMockMvc.perform(post("/api/locadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locador)))
            .andExpect(status().isBadRequest());

        // Validate the Locador in the database
        List<Locador> locadorList = locadorRepository.findAll();
        assertThat(locadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocadors() throws Exception {
        // Initialize the database
        locadorRepository.saveAndFlush(locador);

        // Get all the locadorList
        restLocadorMockMvc.perform(get("/api/locadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locador.getId().intValue())))
            .andExpect(jsonPath("$.[*].imovel").value(hasItem(DEFAULT_IMOVEL.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].idt").value(hasItem(DEFAULT_IDT.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())));
    }
    
    @Test
    @Transactional
    public void getLocador() throws Exception {
        // Initialize the database
        locadorRepository.saveAndFlush(locador);

        // Get the locador
        restLocadorMockMvc.perform(get("/api/locadors/{id}", locador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(locador.getId().intValue()))
            .andExpect(jsonPath("$.imovel").value(DEFAULT_IMOVEL.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.idt").value(DEFAULT_IDT.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocador() throws Exception {
        // Get the locador
        restLocadorMockMvc.perform(get("/api/locadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocador() throws Exception {
        // Initialize the database
        locadorRepository.saveAndFlush(locador);

        int databaseSizeBeforeUpdate = locadorRepository.findAll().size();

        // Update the locador
        Locador updatedLocador = locadorRepository.findById(locador.getId()).get();
        // Disconnect from session so that the updates on updatedLocador are not directly saved in db
        em.detach(updatedLocador);
        updatedLocador
            .imovel(UPDATED_IMOVEL)
            .nome(UPDATED_NOME)
            .end(UPDATED_END)
            .idt(UPDATED_IDT)
            .cpf(UPDATED_CPF);

        restLocadorMockMvc.perform(put("/api/locadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocador)))
            .andExpect(status().isOk());

        // Validate the Locador in the database
        List<Locador> locadorList = locadorRepository.findAll();
        assertThat(locadorList).hasSize(databaseSizeBeforeUpdate);
        Locador testLocador = locadorList.get(locadorList.size() - 1);
        assertThat(testLocador.getImovel()).isEqualTo(UPDATED_IMOVEL);
        assertThat(testLocador.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLocador.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testLocador.getIdt()).isEqualTo(UPDATED_IDT);
        assertThat(testLocador.getCpf()).isEqualTo(UPDATED_CPF);
    }

    @Test
    @Transactional
    public void updateNonExistingLocador() throws Exception {
        int databaseSizeBeforeUpdate = locadorRepository.findAll().size();

        // Create the Locador

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocadorMockMvc.perform(put("/api/locadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locador)))
            .andExpect(status().isBadRequest());

        // Validate the Locador in the database
        List<Locador> locadorList = locadorRepository.findAll();
        assertThat(locadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocador() throws Exception {
        // Initialize the database
        locadorRepository.saveAndFlush(locador);

        int databaseSizeBeforeDelete = locadorRepository.findAll().size();

        // Get the locador
        restLocadorMockMvc.perform(delete("/api/locadors/{id}", locador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Locador> locadorList = locadorRepository.findAll();
        assertThat(locadorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locador.class);
        Locador locador1 = new Locador();
        locador1.setId(1L);
        Locador locador2 = new Locador();
        locador2.setId(locador1.getId());
        assertThat(locador1).isEqualTo(locador2);
        locador2.setId(2L);
        assertThat(locador1).isNotEqualTo(locador2);
        locador1.setId(null);
        assertThat(locador1).isNotEqualTo(locador2);
    }
}
