package br.ufpa.arquivista.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufpa.arquivista.domain.Arquivista;
import br.ufpa.arquivista.repository.ArquivistaRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Arquivista.
 */
@RestController
@RequestMapping("/api")
public class ArquivistaResource {

    private final Logger log = LoggerFactory.getLogger(ArquivistaResource.class);

    private static final String ENTITY_NAME = "arquivista";

    private ArquivistaRepository arquivistaRepository;

    public ArquivistaResource(ArquivistaRepository arquivistaRepository) {
        this.arquivistaRepository = arquivistaRepository;
    }

    /**
     * POST  /arquivistas : Create a new arquivista.
     *
     * @param arquivista the arquivista to create
     * @return the ResponseEntity with status 201 (Created) and with body the new arquivista, or with status 400 (Bad Request) if the arquivista has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/arquivistas")
    @Timed
    public ResponseEntity<Arquivista> createArquivista(@RequestBody Arquivista arquivista) throws URISyntaxException {
        log.debug("REST request to save Arquivista : {}", arquivista);
        if (arquivista.getId() != null) {
            throw new BadRequestAlertException("A new arquivista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Arquivista result = arquivistaRepository.save(arquivista);
        return ResponseEntity.created(new URI("/api/arquivistas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /arquivistas : Updates an existing arquivista.
     *
     * @param arquivista the arquivista to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated arquivista,
     * or with status 400 (Bad Request) if the arquivista is not valid,
     * or with status 500 (Internal Server Error) if the arquivista couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/arquivistas")
    @Timed
    public ResponseEntity<Arquivista> updateArquivista(@RequestBody Arquivista arquivista) throws URISyntaxException {
        log.debug("REST request to update Arquivista : {}", arquivista);
        if (arquivista.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Arquivista result = arquivistaRepository.save(arquivista);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, arquivista.getId().toString()))
            .body(result);
    }

    /**
     * GET  /arquivistas : get all the arquivistas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of arquivistas in body
     */
    @GetMapping("/arquivistas")
    @Timed
    public List<Arquivista> getAllArquivistas(@RequestParam(required = false) String filter,@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("login-is-null".equals(filter)) {
            log.debug("REST request to get all Arquivistas where login is null");
            return StreamSupport
                .stream(arquivistaRepository.findAll().spliterator(), false)
                .filter(arquivista -> arquivista.getLogin() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Arquivistas");
        return arquivistaRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /arquivistas/:id : get the "id" arquivista.
     *
     * @param id the id of the arquivista to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the arquivista, or with status 404 (Not Found)
     */
    @GetMapping("/arquivistas/{id}")
    @Timed
    public ResponseEntity<Arquivista> getArquivista(@PathVariable Long id) {
        log.debug("REST request to get Arquivista : {}", id);
        Optional<Arquivista> arquivista = arquivistaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(arquivista);
    }

    /**
     * DELETE  /arquivistas/:id : delete the "id" arquivista.
     *
     * @param id the id of the arquivista to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/arquivistas/{id}")
    @Timed
    public ResponseEntity<Void> deleteArquivista(@PathVariable Long id) {
        log.debug("REST request to delete Arquivista : {}", id);

        arquivistaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
