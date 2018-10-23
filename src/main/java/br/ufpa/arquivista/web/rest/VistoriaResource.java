package br.ufpa.arquivista.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufpa.arquivista.domain.Vistoria;
import br.ufpa.arquivista.repository.VistoriaRepository;
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
 * REST controller for managing Vistoria.
 */
@RestController
@RequestMapping("/api")
public class VistoriaResource {

    private final Logger log = LoggerFactory.getLogger(VistoriaResource.class);

    private static final String ENTITY_NAME = "vistoria";

    private VistoriaRepository vistoriaRepository;

    public VistoriaResource(VistoriaRepository vistoriaRepository) {
        this.vistoriaRepository = vistoriaRepository;
    }

    /**
     * POST  /vistorias : Create a new vistoria.
     *
     * @param vistoria the vistoria to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vistoria, or with status 400 (Bad Request) if the vistoria has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vistorias")
    @Timed
    public ResponseEntity<Vistoria> createVistoria(@RequestBody Vistoria vistoria) throws URISyntaxException {
        log.debug("REST request to save Vistoria : {}", vistoria);
        if (vistoria.getId() != null) {
            throw new BadRequestAlertException("A new vistoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vistoria result = vistoriaRepository.save(vistoria);
        return ResponseEntity.created(new URI("/api/vistorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vistorias : Updates an existing vistoria.
     *
     * @param vistoria the vistoria to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vistoria,
     * or with status 400 (Bad Request) if the vistoria is not valid,
     * or with status 500 (Internal Server Error) if the vistoria couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vistorias")
    @Timed
    public ResponseEntity<Vistoria> updateVistoria(@RequestBody Vistoria vistoria) throws URISyntaxException {
        log.debug("REST request to update Vistoria : {}", vistoria);
        if (vistoria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vistoria result = vistoriaRepository.save(vistoria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vistoria.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vistorias : get all the vistorias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vistorias in body
     */
    @GetMapping("/vistorias")
    @Timed
    public List<Vistoria> getAllVistorias() {
        log.debug("REST request to get all Vistorias");
        return vistoriaRepository.findAll();
    }

    /**
     * GET  /vistorias/:id : get the "id" vistoria.
     *
     * @param id the id of the vistoria to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vistoria, or with status 404 (Not Found)
     */
    @GetMapping("/vistorias/{id}")
    @Timed
    public ResponseEntity<Vistoria> getVistoria(@PathVariable Long id) {
        log.debug("REST request to get Vistoria : {}", id);
        Optional<Vistoria> vistoria = vistoriaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vistoria);
    }

    /**
     * DELETE  /vistorias/:id : delete the "id" vistoria.
     *
     * @param id the id of the vistoria to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vistorias/{id}")
    @Timed
    public ResponseEntity<Void> deleteVistoria(@PathVariable Long id) {
        log.debug("REST request to delete Vistoria : {}", id);

        vistoriaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
