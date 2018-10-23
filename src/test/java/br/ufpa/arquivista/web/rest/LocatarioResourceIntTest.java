package br.ufpa.arquivista.web.rest;

import br.ufpa.arquivista.ArquivistaApp;

import br.ufpa.arquivista.domain.Locatario;
import br.ufpa.arquivista.repository.LocatarioRepository;
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
 * Test class for the LocatarioResource REST controller.
 *
 * @see LocatarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArquivistaApp.class)
public class LocatarioResourceIntTest {

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
    private LocatarioRepository locatarioRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocatarioMockMvc;

    private Locatario locatario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocatarioResource locatarioResource = new LocatarioResource(locatarioRepository);
        this.restLocatarioMockMvc = MockMvcBuilders.standaloneSetup(locatarioResource)
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
    public static Locatario createEntity(EntityManager em) {
        Locatario locatario = new Locatario()
            .imovel(DEFAULT_IMOVEL)
            .nome(DEFAULT_NOME)
            .end(DEFAULT_END)
            .idt(DEFAULT_IDT)
            .cpf(DEFAULT_CPF);
        return locatario;
    }

    @Before
    public void initTest() {
        locatario = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocatario() throws Exception {
        int databaseSizeBeforeCreate = locatarioRepository.findAll().size();

        // Create the Locatario
        restLocatarioMockMvc.perform(post("/api/locatarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locatario)))
            .andExpect(status().isCreated());

        // Validate the Locatario in the database
        List<Locatario> locatarioList = locatarioRepository.findAll();
        assertThat(locatarioList).hasSize(databaseSizeBeforeCreate + 1);
        Locatario testLocatario = locatarioList.get(locatarioList.size() - 1);
        assertThat(testLocatario.getImovel()).isEqualTo(DEFAULT_IMOVEL);
        assertThat(testLocatario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testLocatario.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testLocatario.getIdt()).isEqualTo(DEFAULT_IDT);
        assertThat(testLocatario.getCpf()).isEqualTo(DEFAULT_CPF);
    }

    @Test
    @Transactional
    public void createLocatarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locatarioRepository.findAll().size();

        // Create the Locatario with an existing ID
        locatario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocatarioMockMvc.perform(post("/api/locatarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locatario)))
            .andExpect(status().isBadRequest());

        // Validate the Locatario in the database
        List<Locatario> locatarioList = locatarioRepository.findAll();
        assertThat(locatarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocatarios() throws Exception {
        // Initialize the database
        locatarioRepository.saveAndFlush(locatario);

        // Get all the locatarioList
        restLocatarioMockMvc.perform(get("/api/locatarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locatario.getId().intValue())))
            .andExpect(jsonPath("$.[*].imovel").value(hasItem(DEFAULT_IMOVEL.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].idt").value(hasItem(DEFAULT_IDT.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())));
    }
    
    @Test
    @Transactional
    public void getLocatario() throws Exception {
        // Initialize the database
        locatarioRepository.saveAndFlush(locatario);

        // Get the locatario
        restLocatarioMockMvc.perform(get("/api/locatarios/{id}", locatario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(locatario.getId().intValue()))
            .andExpect(jsonPath("$.imovel").value(DEFAULT_IMOVEL.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.idt").value(DEFAULT_IDT.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocatario() throws Exception {
        // Get the locatario
        restLocatarioMockMvc.perform(get("/api/locatarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocatario() throws Exception {
        // Initialize the database
        locatarioRepository.saveAndFlush(locatario);

        int databaseSizeBeforeUpdate = locatarioRepository.findAll().size();

        // Update the locatario
        Locatario updatedLocatario = locatarioRepository.findById(locatario.getId()).get();
        // Disconnect from session so that the updates on updatedLocatario are not directly saved in db
        em.detach(updatedLocatario);
        updatedLocatario
            .imovel(UPDATED_IMOVEL)
            .nome(UPDATED_NOME)
            .end(UPDATED_END)
            .idt(UPDATED_IDT)
            .cpf(UPDATED_CPF);

        restLocatarioMockMvc.perform(put("/api/locatarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocatario)))
            .andExpect(status().isOk());

        // Validate the Locatario in the database
        List<Locatario> locatarioList = locatarioRepository.findAll();
        assertThat(locatarioList).hasSize(databaseSizeBeforeUpdate);
        Locatario testLocatario = locatarioList.get(locatarioList.size() - 1);
        assertThat(testLocatario.getImovel()).isEqualTo(UPDATED_IMOVEL);
        assertThat(testLocatario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLocatario.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testLocatario.getIdt()).isEqualTo(UPDATED_IDT);
        assertThat(testLocatario.getCpf()).isEqualTo(UPDATED_CPF);
    }

    @Test
    @Transactional
    public void updateNonExistingLocatario() throws Exception {
        int databaseSizeBeforeUpdate = locatarioRepository.findAll().size();

        // Create the Locatario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocatarioMockMvc.perform(put("/api/locatarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locatario)))
            .andExpect(status().isBadRequest());

        // Validate the Locatario in the database
        List<Locatario> locatarioList = locatarioRepository.findAll();
        assertThat(locatarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocatario() throws Exception {
        // Initialize the database
        locatarioRepository.saveAndFlush(locatario);

        int databaseSizeBeforeDelete = locatarioRepository.findAll().size();

        // Get the locatario
        restLocatarioMockMvc.perform(delete("/api/locatarios/{id}", locatario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Locatario> locatarioList = locatarioRepository.findAll();
        assertThat(locatarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locatario.class);
        Locatario locatario1 = new Locatario();
        locatario1.setId(1L);
        Locatario locatario2 = new Locatario();
        locatario2.setId(locatario1.getId());
        assertThat(locatario1).isEqualTo(locatario2);
        locatario2.setId(2L);
        assertThat(locatario1).isNotEqualTo(locatario2);
        locatario1.setId(null);
        assertThat(locatario1).isNotEqualTo(locatario2);
    }
}
