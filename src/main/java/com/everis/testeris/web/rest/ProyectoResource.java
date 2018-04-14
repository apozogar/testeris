package com.everis.testeris.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.everis.testeris.domain.Proyecto;

import com.everis.testeris.repository.ProyectoRepository;
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
 * REST controller for managing Proyecto.
 */
@RestController
@RequestMapping("/api")
public class ProyectoResource {

    private final Logger log = LoggerFactory.getLogger(ProyectoResource.class);

    private static final String ENTITY_NAME = "proyecto";

    private final ProyectoRepository proyectoRepository;

    public ProyectoResource(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    /**
     * POST  /proyectos : Create a new proyecto.
     *
     * @param proyecto the proyecto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new proyecto, or with status 400 (Bad Request) if the proyecto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/proyectos")
    @Timed
    public ResponseEntity<Proyecto> createProyecto(@RequestBody Proyecto proyecto) throws URISyntaxException {
        log.debug("REST request to save Proyecto : {}", proyecto);
        if (proyecto.getId() != null) {
            throw new BadRequestAlertException("A new proyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Proyecto result = proyectoRepository.save(proyecto);
        return ResponseEntity.created(new URI("/api/proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proyectos : Updates an existing proyecto.
     *
     * @param proyecto the proyecto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated proyecto,
     * or with status 400 (Bad Request) if the proyecto is not valid,
     * or with status 500 (Internal Server Error) if the proyecto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/proyectos")
    @Timed
    public ResponseEntity<Proyecto> updateProyecto(@RequestBody Proyecto proyecto) throws URISyntaxException {
        log.debug("REST request to update Proyecto : {}", proyecto);
        if (proyecto.getId() == null) {
            return createProyecto(proyecto);
        }
        Proyecto result = proyectoRepository.save(proyecto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, proyecto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proyectos : get all the proyectos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of proyectos in body
     */
    @GetMapping("/proyectos")
    @Timed
    public List<Proyecto> getAllProyectos() {
        log.debug("REST request to get all Proyectos");
        return proyectoRepository.findAll();
        }

    /**
     * GET  /proyectos/:id : get the "id" proyecto.
     *
     * @param id the id of the proyecto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the proyecto, or with status 404 (Not Found)
     */
    @GetMapping("/proyectos/{id}")
    @Timed
    public ResponseEntity<Proyecto> getProyecto(@PathVariable Long id) {
        log.debug("REST request to get Proyecto : {}", id);
        Proyecto proyecto = proyectoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(proyecto));
    }

    /**
     * DELETE  /proyectos/:id : delete the "id" proyecto.
     *
     * @param id the id of the proyecto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/proyectos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProyecto(@PathVariable Long id) {
        log.debug("REST request to delete Proyecto : {}", id);
        proyectoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
