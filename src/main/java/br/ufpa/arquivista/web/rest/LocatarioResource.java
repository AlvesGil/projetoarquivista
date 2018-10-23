package br.ufpa.arquivista.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufpa.arquivista.domain.Locatario;
import br.ufpa.arquivista.repository.LocatarioRepository;
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
 * REST controller for managing Locatario.
 */
@RestController
@RequestMapping("/api")
public class LocatarioResource {

    private final Logger log = LoggerFactory.getLogger(LocatarioResource.class);

    private static final String ENTITY_NAME = "locatario";

    private LocatarioRepository locatarioRepository;

    public LocatarioResource(LocatarioRepository locatarioRepository) {
        this.locatarioRepository = locatarioRepository;
    }

    /**
     * POST  /locatarios : Create a new locatario.
     *
     * @param locatario the locatario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locatario, or with status 400 (Bad Request) if the locatario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/locatarios")
    @Timed
    public ResponseEntity<Locatario> createLocatario(@RequestBody Locatario locatario) throws URISyntaxException {
        log.debug("REST request to save Locatario : {}", locatario);
        if (locatario.getId() != null) {
            throw new BadRequestAlertException("A new locatario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Locatario result = locatarioRepository.save(locatario);
        return ResponseEntity.created(new URI("/api/locatarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /locatarios : Updates an existing locatario.
     *
     * @param locatario the locatario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locatario,
     * or with status 400 (Bad Request) if the locatario is not valid,
     * or with status 500 (Internal Server Error) if the locatario couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/locatarios")
    @Timed
    public ResponseEntity<Locatario> updateLocatario(@RequestBody Locatario locatario) throws URISyntaxException {
        log.debug("REST request to update Locatario : {}", locatario);
        if (locatario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Locatario result = locatarioRepository.save(locatario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, locatario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /locatarios : get all the locatarios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of locatarios in body
     */
    @GetMapping("/locatarios")
    @Timed
    public List<Locatario> getAllLocatarios() {
        log.debug("REST request to get all Locatarios");
        return locatarioRepository.findAll();
    }

    /**
     * GET  /locatarios/:id : get the "id" locatario.
     *
     * @param id the id of the locatario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locatario, or with status 404 (Not Found)
     */
    @GetMapping("/locatarios/{id}")
    @Timed
    public ResponseEntity<Locatario> getLocatario(@PathVariable Long id) {
        log.debug("REST request to get Locatario : {}", id);
        Optional<Locatario> locatario = locatarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locatario);
    }

    /**
     * DELETE  /locatarios/:id : delete the "id" locatario.
     *
     * @param id the id of the locatario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/locatarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocatario(@PathVariable Long id) {
        log.debug("REST request to delete Locatario : {}", id);

        locatarioRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
