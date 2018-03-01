package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Layer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Layer.
 */
public interface LayerService {

    /**
     * Save a layer.
     *
     * @param layer the entity to save
     * @return the persisted entity
     */
    Layer save(Layer layer);

    /**
     * Get all the layers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Layer> findAll(Pageable pageable);

    /**
     * Get the "id" layer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Layer findOne(Long id);

    /**
     * Delete the "id" layer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
