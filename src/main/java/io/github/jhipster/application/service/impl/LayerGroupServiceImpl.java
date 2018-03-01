package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.LayerGroupService;
import io.github.jhipster.application.domain.LayerGroup;
import io.github.jhipster.application.repository.LayerGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing LayerGroup.
 */
@Service
@Transactional
public class LayerGroupServiceImpl implements LayerGroupService {

    private final Logger log = LoggerFactory.getLogger(LayerGroupServiceImpl.class);

    private final LayerGroupRepository layerGroupRepository;

    public LayerGroupServiceImpl(LayerGroupRepository layerGroupRepository) {
        this.layerGroupRepository = layerGroupRepository;
    }

    /**
     * Save a layerGroup.
     *
     * @param layerGroup the entity to save
     * @return the persisted entity
     */
    @Override
    public LayerGroup save(LayerGroup layerGroup) {
        log.debug("Request to save LayerGroup : {}", layerGroup);
        return layerGroupRepository.save(layerGroup);
    }

    /**
     * Get all the layerGroups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LayerGroup> findAll(Pageable pageable) {
        log.debug("Request to get all LayerGroups");
        return layerGroupRepository.findAll(pageable);
    }

    /**
     * Get one layerGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LayerGroup findOne(Long id) {
        log.debug("Request to get LayerGroup : {}", id);
        return layerGroupRepository.findOne(id);
    }

    /**
     * Delete the layerGroup by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LayerGroup : {}", id);
        layerGroupRepository.delete(id);
    }
}
