package br.ufpa.arquivista.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufpa.arquivista.domain.Comprovante;
import br.ufpa.arquivista.repository.ComprovanteRepository;
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
 * REST controller for managing Comprovante.
 */
@RestController
@RequestMapping("/api")
public class ComprovanteResource {

    private final Logger log = LoggerFactory.getLogger(ComprovanteResource.class);

    private static final String ENTITY_NAME = "comprovante";

    private ComprovanteRepository comprovanteRepository;

    public ComprovanteResource(ComprovanteRepository comprovanteRepository) {
        this.comprovanteRepository = comprovanteRepository;
    }

    /**
     * POST  /comprovantes : Create a new comprovante.
     *
     * @param comprovante the comprovante to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comprovante, or with status 400 (Bad Request) if the comprovante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comprovantes")
    @Timed
    public ResponseEntity<Comprovante> createComprovante(@RequestBody Comprovante comprovante) throws URISyntaxException {
        log.debug("REST request to save Comprovante : {}", comprovante);
        if (comprovante.getId() != null) {
            throw new BadRequestAlertException("A new comprovante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Comprovante result = comprovanteRepository.save(comprovante);
        return ResponseEntity.created(new URI("/api/comprovantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comprovantes : Updates an existing comprovante.
     *
     * @param comprovante the comprovante to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comprovante,
     * or with status 400 (Bad Request) if the comprovante is not valid,
     * or with status 500 (Internal Server Error) if the comprovante couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comprovantes")
    @Timed
    public ResponseEntity<Comprovante> updateComprovante(@RequestBody Comprovante comprovante) throws URISyntaxException {
        log.debug("REST request to update Comprovante : {}", comprovante);
        if (comprovante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Comprovante result = comprovanteRepository.save(comprovante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comprovante.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comprovantes : get all the comprovantes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of comprovantes in body
     */
    @GetMapping("/comprovantes")
    @Timed
    public List<Comprovante> getAllComprovantes() {
        log.debug("REST request to get all Comprovantes");
        return comprovanteRepository.findAll();
    }

    /**
     * GET  /comprovantes/:id : get the "id" comprovante.
     *
     * @param id the id of the comprovante to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comprovante, or with status 404 (Not Found)
     */
    @GetMapping("/comprovantes/{id}")
    @Timed
    public ResponseEntity<Comprovante> getComprovante(@PathVariable Long id) {
        log.debug("REST request to get Comprovante : {}", id);
        Optional<Comprovante> comprovante = comprovanteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(comprovante);
    }

    /**
     * DELETE  /comprovantes/:id : delete the "id" comprovante.
     *
     * @param id the id of the comprovante to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comprovantes/{id}")
    @Timed
    public ResponseEntity<Void> deleteComprovante(@PathVariable Long id) {
        log.debug("REST request to delete Comprovante : {}", id);

        comprovanteRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
