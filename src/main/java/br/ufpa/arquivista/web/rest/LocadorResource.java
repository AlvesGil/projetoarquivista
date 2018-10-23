package br.ufpa.arquivista.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufpa.arquivista.domain.Locador;
import br.ufpa.arquivista.repository.LocadorRepository;
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
 * REST controller for managing Locador.
 */
@RestController
@RequestMapping("/api")
public class LocadorResource {

    private final Logger log = LoggerFactory.getLogger(LocadorResource.class);

    private static final String ENTITY_NAME = "locador";

    private LocadorRepository locadorRepository;

    public LocadorResource(LocadorRepository locadorRepository) {
        this.locadorRepository = locadorRepository;
    }

    /**
     * POST  /locadors : Create a new locador.
     *
     * @param locador the locador to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locador, or with status 400 (Bad Request) if the locador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/locadors")
    @Timed
    public ResponseEntity<Locador> createLocador(@RequestBody Locador locador) throws URISyntaxException {
        log.debug("REST request to save Locador : {}", locador);
        if (locador.getId() != null) {
            throw new BadRequestAlertException("A new locador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Locador result = locadorRepository.save(locador);
        return ResponseEntity.created(new URI("/api/locadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /locadors : Updates an existing locador.
     *
     * @param locador the locador to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locador,
     * or with status 400 (Bad Request) if the locador is not valid,
     * or with status 500 (Internal Server Error) if the locador couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/locadors")
    @Timed
    public ResponseEntity<Locador> updateLocador(@RequestBody Locador locador) throws URISyntaxException {
        log.debug("REST request to update Locador : {}", locador);
        if (locador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Locador result = locadorRepository.save(locador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, locador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /locadors : get all the locadors.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of locadors in body
     */
    @GetMapping("/locadors")
    @Timed
    public List<Locador> getAllLocadors(@RequestParam(required = false) String filter) {
        if ("login-is-null".equals(filter)) {
            log.debug("REST request to get all Locadors where login is null");
            return StreamSupport
                .stream(locadorRepository.findAll().spliterator(), false)
                .filter(locador -> locador.getLogin() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Locadors");
        return locadorRepository.findAll();
    }

    /**
     * GET  /locadors/:id : get the "id" locador.
     *
     * @param id the id of the locador to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locador, or with status 404 (Not Found)
     */
    @GetMapping("/locadors/{id}")
    @Timed
    public ResponseEntity<Locador> getLocador(@PathVariable Long id) {
        log.debug("REST request to get Locador : {}", id);
        Optional<Locador> locador = locadorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locador);
    }

    /**
     * DELETE  /locadors/:id : delete the "id" locador.
     *
     * @param id the id of the locador to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/locadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocador(@PathVariable Long id) {
        log.debug("REST request to delete Locador : {}", id);

        locadorRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
