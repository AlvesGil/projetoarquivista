package br.ufpa.arquivista.web.rest;

import br.ufpa.arquivista.ArquivistaApp;

import br.ufpa.arquivista.domain.Pagamento;
import br.ufpa.arquivista.repository.PagamentoRepository;
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
 * Test class for the PagamentoResource REST controller.
 *
 * @see PagamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArquivistaApp.class)
public class PagamentoResourceIntTest {

    private static final String DEFAULT_CONDOMINIO = "AAAAAAAAAA";
    private static final String UPDATED_CONDOMINIO = "BBBBBBBBBB";

    private static final String DEFAULT_IPTU = "AAAAAAAAAA";
    private static final String UPDATED_IPTU = "BBBBBBBBBB";

    private static final String DEFAULT_LUZ = "AAAAAAAAAA";
    private static final String UPDATED_LUZ = "BBBBBBBBBB";

    private static final String DEFAULT_AGUA = "AAAAAAAAAA";
    private static final String UPDATED_AGUA = "BBBBBBBBBB";

    private static final String DEFAULT_DIVERSOS = "AAAAAAAAAA";
    private static final String UPDATED_DIVERSOS = "BBBBBBBBBB";

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPagamentoMockMvc;

    private Pagamento pagamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PagamentoResource pagamentoResource = new PagamentoResource(pagamentoRepository);
        this.restPagamentoMockMvc = MockMvcBuilders.standaloneSetup(pagamentoResource)
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
    public static Pagamento createEntity(EntityManager em) {
        Pagamento pagamento = new Pagamento()
            .condominio(DEFAULT_CONDOMINIO)
            .iptu(DEFAULT_IPTU)
            .luz(DEFAULT_LUZ)
            .agua(DEFAULT_AGUA)
            .diversos(DEFAULT_DIVERSOS);
        return pagamento;
    }

    @Before
    public void initTest() {
        pagamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createPagamento() throws Exception {
        int databaseSizeBeforeCreate = pagamentoRepository.findAll().size();

        // Create the Pagamento
        restPagamentoMockMvc.perform(post("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamento)))
            .andExpect(status().isCreated());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Pagamento testPagamento = pagamentoList.get(pagamentoList.size() - 1);
        assertThat(testPagamento.getCondominio()).isEqualTo(DEFAULT_CONDOMINIO);
        assertThat(testPagamento.getIptu()).isEqualTo(DEFAULT_IPTU);
        assertThat(testPagamento.getLuz()).isEqualTo(DEFAULT_LUZ);
        assertThat(testPagamento.getAgua()).isEqualTo(DEFAULT_AGUA);
        assertThat(testPagamento.getDiversos()).isEqualTo(DEFAULT_DIVERSOS);
    }

    @Test
    @Transactional
    public void createPagamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pagamentoRepository.findAll().size();

        // Create the Pagamento with an existing ID
        pagamento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagamentoMockMvc.perform(post("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamento)))
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPagamentos() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList
        restPagamentoMockMvc.perform(get("/api/pagamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].condominio").value(hasItem(DEFAULT_CONDOMINIO.toString())))
            .andExpect(jsonPath("$.[*].iptu").value(hasItem(DEFAULT_IPTU.toString())))
            .andExpect(jsonPath("$.[*].luz").value(hasItem(DEFAULT_LUZ.toString())))
            .andExpect(jsonPath("$.[*].agua").value(hasItem(DEFAULT_AGUA.toString())))
            .andExpect(jsonPath("$.[*].diversos").value(hasItem(DEFAULT_DIVERSOS.toString())));
    }
    
    @Test
    @Transactional
    public void getPagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get the pagamento
        restPagamentoMockMvc.perform(get("/api/pagamentos/{id}", pagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pagamento.getId().intValue()))
            .andExpect(jsonPath("$.condominio").value(DEFAULT_CONDOMINIO.toString()))
            .andExpect(jsonPath("$.iptu").value(DEFAULT_IPTU.toString()))
            .andExpect(jsonPath("$.luz").value(DEFAULT_LUZ.toString()))
            .andExpect(jsonPath("$.agua").value(DEFAULT_AGUA.toString()))
            .andExpect(jsonPath("$.diversos").value(DEFAULT_DIVERSOS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPagamento() throws Exception {
        // Get the pagamento
        restPagamentoMockMvc.perform(get("/api/pagamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();

        // Update the pagamento
        Pagamento updatedPagamento = pagamentoRepository.findById(pagamento.getId()).get();
        // Disconnect from session so that the updates on updatedPagamento are not directly saved in db
        em.detach(updatedPagamento);
        updatedPagamento
            .condominio(UPDATED_CONDOMINIO)
            .iptu(UPDATED_IPTU)
            .luz(UPDATED_LUZ)
            .agua(UPDATED_AGUA)
            .diversos(UPDATED_DIVERSOS);

        restPagamentoMockMvc.perform(put("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPagamento)))
            .andExpect(status().isOk());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
        Pagamento testPagamento = pagamentoList.get(pagamentoList.size() - 1);
        assertThat(testPagamento.getCondominio()).isEqualTo(UPDATED_CONDOMINIO);
        assertThat(testPagamento.getIptu()).isEqualTo(UPDATED_IPTU);
        assertThat(testPagamento.getLuz()).isEqualTo(UPDATED_LUZ);
        assertThat(testPagamento.getAgua()).isEqualTo(UPDATED_AGUA);
        assertThat(testPagamento.getDiversos()).isEqualTo(UPDATED_DIVERSOS);
    }

    @Test
    @Transactional
    public void updateNonExistingPagamento() throws Exception {
        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();

        // Create the Pagamento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagamentoMockMvc.perform(put("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamento)))
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        int databaseSizeBeforeDelete = pagamentoRepository.findAll().size();

        // Get the pagamento
        restPagamentoMockMvc.perform(delete("/api/pagamentos/{id}", pagamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pagamento.class);
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setId(1L);
        Pagamento pagamento2 = new Pagamento();
        pagamento2.setId(pagamento1.getId());
        assertThat(pagamento1).isEqualTo(pagamento2);
        pagamento2.setId(2L);
        assertThat(pagamento1).isNotEqualTo(pagamento2);
        pagamento1.setId(null);
        assertThat(pagamento1).isNotEqualTo(pagamento2);
    }
}
