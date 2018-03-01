package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.LayerService;
import io.github.jhipster.application.domain.Layer;
import io.github.jhipster.application.repository.LayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Layer.
 */
@Service
@Transactional
public class LayerServiceImpl implements LayerService {

    private final Logger log = LoggerFactory.getLogger(LayerServiceImpl.class);

    private final LayerRepository layerRepository;

    public LayerServiceImpl(LayerRepository layerRepository) {
        this.layerRepository = layerRepository;
    }

    /**
     * Save a layer.
     *
     * @param layer the entity to save
     * @return the persisted entity
     */
    @Override
    public Layer save(Layer layer) {
        log.debug("Request to save Layer : {}", layer);
        return layerRepository.save(layer);
    }

    /**
     * Get all the layers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Layer> findAll(Pageable pageable) {
        log.debug("Request to get all Layers");
        return layerRepository.findAll(pageable);
    }

    /**
     * Get one layer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Layer findOne(Long id) {
        log.debug("Request to get Layer : {}", id);
        return layerRepository.findOne(id);
    }

    /**
     * Delete the layer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Layer : {}", id);
        layerRepository.delete(id);
    }
}
