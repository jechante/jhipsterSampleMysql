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

import io.github.jhipster.application.domain.Layer;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.LayerRepository;
import io.github.jhipster.application.service.dto.LayerCriteria;

import io.github.jhipster.application.domain.enumeration.ServerType;
import io.github.jhipster.application.domain.enumeration.PointQueryType;
import io.github.jhipster.application.domain.enumeration.PoiType;

/**
 * Service for executing complex queries for Layer entities in the database.
 * The main input is a {@link LayerCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Layer} or a {@link Page} of {@link Layer} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LayerQueryService extends QueryService<Layer> {

    private final Logger log = LoggerFactory.getLogger(LayerQueryService.class);


    private final LayerRepository layerRepository;

    public LayerQueryService(LayerRepository layerRepository) {
        this.layerRepository = layerRepository;
    }

    /**
     * Return a {@link List} of {@link Layer} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Layer> findByCriteria(LayerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Layer> specification = createSpecification(criteria);
        return layerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Layer} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Layer> findByCriteria(LayerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Layer> specification = createSpecification(criteria);
        return layerRepository.findAll(specification, page);
    }

    /**
     * Function to convert LayerCriteria to a {@link Specifications}
     */
    private Specifications<Layer> createSpecification(LayerCriteria criteria) {
        Specifications<Layer> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Layer_.id));
            }
            if (criteria.getLayerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLayerName(), Layer_.layerName));
            }
            if (criteria.getIdentifier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdentifier(), Layer_.identifier));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildSpecification(criteria.getSource(), Layer_.source));
            }
            if (criteria.getPointQueryType() != null) {
                specification = specification.and(buildSpecification(criteria.getPointQueryType(), Layer_.pointQueryType));
            }
            if (criteria.getStyle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStyle(), Layer_.style));
            }
            if (criteria.getPoiType() != null) {
                specification = specification.and(buildSpecification(criteria.getPoiType(), Layer_.poiType));
            }
            if (criteria.getPoiURL() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPoiURL(), Layer_.poiURL));
            }
            if (criteria.getLegendsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLegendsId(), Layer_.legends, Legend_.id));
            }
            if (criteria.getGisServerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getGisServerId(), Layer_.gisServer, GisServer_.id));
            }
            if (criteria.getLayerGroupId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLayerGroupId(), Layer_.layerGroup, LayerGroup_.id));
            }
        }
        return specification;
    }

}
