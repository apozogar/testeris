package com.everis.testeris.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.everis.testeris.domain.Paso;

import com.everis.testeris.repository.PasoRepository;
import com.everis.testeris.web.rest.errors.BadRequestAlertException;
import com.everis.testeris.web.rest.util.HeaderUtil;
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
 * REST controller for managing Paso.
 */
@RestController
@RequestMapping("/api")
public class PasoResource {

    private final Logger log = LoggerFactory.getLogger(PasoResource.class);

    private static final String ENTITY_NAME = "paso";

    private final PasoRepository pasoRepository;

    public PasoResource(PasoRepository pasoRepository) {
        this.pasoRepository = pasoRepository;
    }

    /**
     * POST  /pasos : Create a new paso.
     *
     * @param paso the paso to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paso, or with status 400 (Bad Request) if the paso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pasos")
    @Timed
    public ResponseEntity<Paso> createPaso(@RequestBody Paso paso) throws URISyntaxException {
        log.debug("REST request to save Paso : {}", paso);
        if (paso.getId() != null) {
            throw new BadRequestAlertException("A new paso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Paso result = pasoRepository.save(paso);
        return ResponseEntity.created(new URI("/api/pasos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pasos : Updates an existing paso.
     *
     * @param paso the paso to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paso,
     * or with status 400 (Bad Request) if the paso is not valid,
     * or with status 500 (Internal Server Error) if the paso couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pasos")
    @Timed
    public ResponseEntity<Paso> updatePaso(@RequestBody Paso paso) throws URISyntaxException {
        log.debug("REST request to update Paso : {}", paso);
        if (paso.getId() == null) {
            return createPaso(paso);
        }
        Paso result = pasoRepository.save(paso);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paso.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pasos : get all the pasos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pasos in body
     */
    @GetMapping("/pasos")
    @Timed
    public List<Paso> getAllPasos() {
        log.debug("REST request to get all Pasos");
        return pasoRepository.findAll();
        }

    /**
     * GET  /pasos/:id : get the "id" paso.
     *
     * @param id the id of the paso to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paso, or with status 404 (Not Found)
     */
    @GetMapping("/pasos/{id}")
    @Timed
    public ResponseEntity<Paso> getPaso(@PathVariable Long id) {
        log.debug("REST request to get Paso : {}", id);
        Paso paso = pasoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paso));
    }

    /**
     * DELETE  /pasos/:id : delete the "id" paso.
     *
     * @param id the id of the paso to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pasos/{id}")
    @Timed
    public ResponseEntity<Void> deletePaso(@PathVariable Long id) {
        log.debug("REST request to delete Paso : {}", id);
        pasoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
