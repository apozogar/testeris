package com.everis.testeris.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.everis.testeris.domain.Caso;

import com.everis.testeris.repository.CasoRepository;
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
 * REST controller for managing Caso.
 */
@RestController
@RequestMapping("/api")
public class CasoResource {

    private final Logger log = LoggerFactory.getLogger(CasoResource.class);

    private static final String ENTITY_NAME = "caso";

    private final CasoRepository casoRepository;

    public CasoResource(CasoRepository casoRepository) {
        this.casoRepository = casoRepository;
    }

    /**
     * POST  /casos : Create a new caso.
     *
     * @param caso the caso to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caso, or with status 400 (Bad Request) if the caso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/casos")
    @Timed
    public ResponseEntity<Caso> createCaso(@RequestBody Caso caso) throws URISyntaxException {
        log.debug("REST request to save Caso : {}", caso);
        if (caso.getId() != null) {
            throw new BadRequestAlertException("A new caso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Caso result = casoRepository.save(caso);
        return ResponseEntity.created(new URI("/api/casos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /casos : Updates an existing caso.
     *
     * @param caso the caso to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated caso,
     * or with status 400 (Bad Request) if the caso is not valid,
     * or with status 500 (Internal Server Error) if the caso couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/casos")
    @Timed
    public ResponseEntity<Caso> updateCaso(@RequestBody Caso caso) throws URISyntaxException {
        log.debug("REST request to update Caso : {}", caso);
        if (caso.getId() == null) {
            return createCaso(caso);
        }
        Caso result = casoRepository.save(caso);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, caso.getId().toString()))
            .body(result);
    }

    /**
     * GET  /casos : get all the casos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of casos in body
     */
    @GetMapping("/casos")
    @Timed
    public List<Caso> getAllCasos() {
        log.debug("REST request to get all Casos");
        return casoRepository.findAll();
        }

    /**
     * GET  /casos/:id : get the "id" caso.
     *
     * @param id the id of the caso to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caso, or with status 404 (Not Found)
     */
    @GetMapping("/casos/{id}")
    @Timed
    public ResponseEntity<Caso> getCaso(@PathVariable Long id) {
        log.debug("REST request to get Caso : {}", id);
        Caso caso = casoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(caso));
    }

    /**
     * DELETE  /casos/:id : delete the "id" caso.
     *
     * @param id the id of the caso to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/casos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCaso(@PathVariable Long id) {
        log.debug("REST request to delete Caso : {}", id);
        casoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
