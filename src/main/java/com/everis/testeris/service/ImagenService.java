package com.everis.testeris.service;

import com.everis.testeris.domain.Imagen;
import java.util.List;

/**
 * Service Interface for managing Imagen.
 */
public interface ImagenService {

    /**
     * Save a imagen.
     *
     * @param imagen the entity to save
     * @return the persisted entity
     */
    Imagen save(Imagen imagen);

    /**
     * Get all the imagens.
     *
     * @return the list of entities
     */
    List<Imagen> findAll();

    /**
     * Get the "id" imagen.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Imagen findOne(Long id);

    /**
     * Delete the "id" imagen.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
