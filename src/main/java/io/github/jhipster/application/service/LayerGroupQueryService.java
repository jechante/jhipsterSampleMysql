package io.github.jhipster.application.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.github.jhipster.application.domain.LayerGroup;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.LayerGroupRepository;
import io.github.jhipster.application.service.dto.LayerGroupCriteria;


/**
 * Service for executing complex queries for LayerGroup entities in the database.
 * The main input is a {@link LayerGroupCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LayerGroup} or a {@link Page} of {@link LayerGroup} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LayerGroupQueryService extends QueryService<LayerGroup> {

    private final Logger log = LoggerFactory.getLogger(LayerGroupQueryService.class);


    private final LayerGroupRepository layerGroupRepository;

    public LayerGroupQueryService(LayerGroupRepository layerGroupRepository) {
        this.layerGroupRepository = layerGroupRepository;
    }

    /**
     * Return a {@link List} of {@link LayerGroup} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LayerGroup> findByCriteria(LayerGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<LayerGroup> specification = createSpecification(criteria);
        return layerGroupRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LayerGroup} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LayerGroup> findByCriteria(LayerGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<LayerGroup> specification = createSpecification(criteria);
        return layerGroupRepository.findAll(specification, page);
    }

    /**
     * Function to convert LayerGroupCriteria to a {@link Specifications}
     */
    private Specifications<LayerGroup> createSpecification(LayerGroupCriteria criteria) {
        Specifications<LayerGroup> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), LayerGroup_.id));
            }
            if (criteria.getGroupName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupName(), LayerGroup_.groupName));
            }
            if (criteria.getLayersId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLayersId(), LayerGroup_.layers, Layer_.id));
            }
            if (criteria.getSubGroupsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSubGroupsId(), LayerGroup_.subGroups, LayerGroup_.id));
            }
            if (criteria.getParentGroupId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getParentGroupId(), LayerGroup_.parentGroup, LayerGroup_.id));
            }
        }
        return specification;
    }

}
