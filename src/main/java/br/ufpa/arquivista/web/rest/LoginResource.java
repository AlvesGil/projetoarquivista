package br.ufpa.arquivista.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufpa.arquivista.domain.Login;
import br.ufpa.arquivista.repository.LoginRepository;
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
 * REST controller for managing Login.
 */
@RestController
@RequestMapping("/api")
public class LoginResource {

    private final Logger log = LoggerFactory.getLogger(LoginResource.class);

    private static final String ENTITY_NAME = "login";

    private LoginRepository loginRepository;

    public LoginResource(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    /**
     * POST  /logins : Create a new login.
     *
     * @param login the login to create
     * @return the ResponseEntity with status 201 (Created) and with body the new login, or with status 400 (Bad Request) if the login has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/logins")
    @Timed
    public ResponseEntity<Login> createLogin(@RequestBody Login login) throws URISyntaxException {
        log.debug("REST request to save Login : {}", login);
        if (login.getId() != null) {
            throw new BadRequestAlertException("A new login cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Login result = loginRepository.save(login);
        return ResponseEntity.created(new URI("/api/logins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /logins : Updates an existing login.
     *
     * @param login the login to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated login,
     * or with status 400 (Bad Request) if the login is not valid,
     * or with status 500 (Internal Server Error) if the login couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/logins")
    @Timed
    public ResponseEntity<Login> updateLogin(@RequestBody Login login) throws URISyntaxException {
        log.debug("REST request to update Login : {}", login);
        if (login.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Login result = loginRepository.save(login);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, login.getId().toString()))
            .body(result);
    }

    /**
     * GET  /logins : get all the logins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of logins in body
     */
    @GetMapping("/logins")
    @Timed
    public List<Login> getAllLogins() {
        log.debug("REST request to get all Logins");
        return loginRepository.findAll();
    }

    /**
     * GET  /logins/:id : get the "id" login.
     *
     * @param id the id of the login to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the login, or with status 404 (Not Found)
     */
    @GetMapping("/logins/{id}")
    @Timed
    public ResponseEntity<Login> getLogin(@PathVariable Long id) {
        log.debug("REST request to get Login : {}", id);
        Optional<Login> login = loginRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(login);
    }

    /**
     * DELETE  /logins/:id : delete the "id" login.
     *
     * @param id the id of the login to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/logins/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogin(@PathVariable Long id) {
        log.debug("REST request to delete Login : {}", id);

        loginRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
