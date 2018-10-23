package br.ufpa.arquivista.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufpa.arquivista.domain.Pagamento;
import br.ufpa.arquivista.repository.PagamentoRepository;
import br.ufpa.arquivista.web.rest.errors.BadRequestAlertException;
import br.ufpa.arquivista.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pagamento.
 */
@RestController
@RequestMapping("/api")
public class PagamentoResource {

    private final Logger log = LoggerFactory.getLogger(PagamentoResource.class);

    private static final String ENTITY_NAME = "pagamento";

    private PagamentoRepository pagamentoRepository;

    public PagamentoResource(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    /**
     * POST  /pagamentos : Create a new pagamento.
     *
     * @param pagamento the pagamento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pagamento, or with status 400 (Bad Request) if the pagamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pagamentos")
    @Timed
    public ResponseEntity<Pagamento> createPagamento(@RequestBody Pagamento pagamento) throws URISyntaxException {
        log.debug("REST request to save Pagamento : {}", pagamento);
        if (pagamento.getId() != null) {
            throw new BadRequestAlertException("A new pagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pagamento result = pagamentoRepository.save(pagamento);
        return ResponseEntity.created(new URI("/api/pagamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pagamentos : Updates an existing pagamento.
     *
     * @param pagamento the pagamento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pagamento,
     * or with status 400 (Bad Request) if the pagamento is not valid,
     * or with status 500 (Internal Server Error) if the pagamento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pagamentos")
    @Timed
    public ResponseEntity<Pagamento> updatePagamento(@RequestBody Pagamento pagamento) throws URISyntaxException {
        log.debug("REST request to update Pagamento : {}", pagamento);
        if (pagamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pagamento result = pagamentoRepository.save(pagamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pagamento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pagamentos : get all the pagamentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pagamentos in body
     */
    @GetMapping("/pagamentos")
    @Timed
    public List<Pagamento> getAllPagamentos() {
        log.debug("REST request to get all Pagamentos");
        return pagamentoRepository.findAll();
    }

    /**
     * GET  /pagamentos/:id : get the "id" pagamento.
     *
     * @param id the id of the pagamento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pagamento, or with status 404 (Not Found)
     */
    @GetMapping("/pagamentos/{id}")
    @Timed
    public ResponseEntity<Pagamento> getPagamento(@PathVariable Long id) {
        log.debug("REST request to get Pagamento : {}", id);
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pagamento);
    }

    /**
     * DELETE  /pagamentos/:id : delete the "id" pagamento.
     *
     * @param id the id of the pagamento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pagamentos/{id}")
    @Timed
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        log.debug("REST request to delete Pagamento : {}", id);

        pagamentoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
