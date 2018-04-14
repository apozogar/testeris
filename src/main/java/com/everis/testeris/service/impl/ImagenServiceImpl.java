package com.everis.testeris.service.impl;

import com.everis.testeris.service.ImagenService;
import com.everis.testeris.domain.Imagen;
import com.everis.testeris.repository.ImagenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Imagen.
 */
@Service
@Transactional
public class ImagenServiceImpl implements ImagenService {

    private final Logger log = LoggerFactory.getLogger(ImagenServiceImpl.class);

    private final ImagenRepository imagenRepository;

    public ImagenServiceImpl(ImagenRepository imagenRepository) {
        this.imagenRepository = imagenRepository;
    }

    /**
     * Save a imagen.
     *
     * @param imagen the entity to save
     * @return the persisted entity
     */
    @Override
    public Imagen save(Imagen imagen) {
        log.debug("Request to save Imagen : {}", imagen);
        return imagenRepository.save(imagen);
    }

    /**
     * Get all the imagens.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Imagen> findAll() {
        log.debug("Request to get all Imagens");
        return imagenRepository.findAll();
    }

    /**
     * Get one imagen by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Imagen findOne(Long id) {
        log.debug("Request to get Imagen : {}", id);
        return imagenRepository.findOne(id);
    }

    /**
     * Delete the imagen by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Imagen : {}", id);
        imagenRepository.delete(id);
    }
}
