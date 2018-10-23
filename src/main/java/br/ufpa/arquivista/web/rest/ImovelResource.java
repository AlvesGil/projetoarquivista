package br.ufpa.arquivista.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufpa.arquivista.domain.Imovel;
import br.ufpa.arquivista.repository.ImovelRepository;
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
 * REST controller for managing Imovel.
 */
@RestController
@RequestMapping("/api")
public class ImovelResource {

    private final Logger log = LoggerFactory.getLogger(ImovelResource.class);

    private static final String ENTITY_NAME = "imovel";

    private ImovelRepository imovelRepository;

    public ImovelResource(ImovelRepository imovelRepository) {
        this.imovelRepository = imovelRepository;
    }

    /**
     * POST  /imovels : Create a new imovel.
     *
     * @param imovel the imovel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imovel, or with status 400 (Bad Request) if the imovel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/imovels")
    @Timed
    public ResponseEntity<Imovel> createImovel(@RequestBody Imovel imovel) throws URISyntaxException {
        log.debug("REST request to save Imovel : {}", imovel);
        if (imovel.getId() != null) {
            throw new BadRequestAlertException("A new imovel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Imovel result = imovelRepository.save(imovel);
        return ResponseEntity.created(new URI("/api/imovels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /imovels : Updates an existing imovel.
     *
     * @param imovel the imovel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imovel,
     * or with status 400 (Bad Request) if the imovel is not valid,
     * or with status 500 (Internal Server Error) if the imovel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/imovels")
    @Timed
    public ResponseEntity<Imovel> updateImovel(@RequestBody Imovel imovel) throws URISyntaxException {
        log.debug("REST request to update Imovel : {}", imovel);
        if (imovel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Imovel result = imovelRepository.save(imovel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, imovel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /imovels : get all the imovels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of imovels in body
     */
    @GetMapping("/imovels")
    @Timed
    public List<Imovel> getAllImovels() {
        log.debug("REST request to get all Imovels");
        return imovelRepository.findAll();
    }

    /**
     * GET  /imovels/:id : get the "id" imovel.
     *
     * @param id the id of the imovel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imovel, or with status 404 (Not Found)
     */
    @GetMapping("/imovels/{id}")
    @Timed
    public ResponseEntity<Imovel> getImovel(@PathVariable Long id) {
        log.debug("REST request to get Imovel : {}", id);
        Optional<Imovel> imovel = imovelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(imovel);
    }

    /**
     * DELETE  /imovels/:id : delete the "id" imovel.
     *
     * @param id the id of the imovel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/imovels/{id}")
    @Timed
    public ResponseEntity<Void> deleteImovel(@PathVariable Long id) {
        log.debug("REST request to delete Imovel : {}", id);

        imovelRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
