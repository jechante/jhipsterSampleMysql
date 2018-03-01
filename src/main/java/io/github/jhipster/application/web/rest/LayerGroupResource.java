package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.LayerGroup;
import io.github.jhipster.application.service.LayerGroupService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.LayerGroupCriteria;
import io.github.jhipster.application.service.LayerGroupQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LayerGroup.
 */
@RestController
@RequestMapping("/api")
public class LayerGroupResource {

    private final Logger log = LoggerFactory.getLogger(LayerGroupResource.class);

    private static final String ENTITY_NAME = "layerGroup";

    private final LayerGroupService layerGroupService;

    private final LayerGroupQueryService layerGroupQueryService;

    public LayerGroupResource(LayerGroupService layerGroupService, LayerGroupQueryService layerGroupQueryService) {
        this.layerGroupService = layerGroupService;
        this.layerGroupQueryService = layerGroupQueryService;
    }

    /**
     * POST  /layer-groups : Create a new layerGroup.
     *
     * @param layerGroup the layerGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new layerGroup, or with status 400 (Bad Request) if the layerGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/layer-groups")
    @Timed
    public ResponseEntity<LayerGroup> createLayerGroup(@RequestBody LayerGroup layerGroup) throws URISyntaxException {
        log.debug("REST request to save LayerGroup : {}", layerGroup);
        if (layerGroup.getId() != null) {
            throw new BadRequestAlertException("A new layerGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LayerGroup result = layerGroupService.save(layerGroup);
        return ResponseEntity.created(new URI("/api/layer-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /layer-groups : Updates an existing layerGroup.
     *
     * @param layerGroup the layerGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated layerGroup,
     * or with status 400 (Bad Request) if the layerGroup is not valid,
     * or with status 500 (Internal Server Error) if the layerGroup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/layer-groups")
    @Timed
    public ResponseEntity<LayerGroup> updateLayerGroup(@RequestBody LayerGroup layerGroup) throws URISyntaxException {
        log.debug("REST request to update LayerGroup : {}", layerGroup);
        if (layerGroup.getId() == null) {
            return createLayerGroup(layerGroup);
        }
        LayerGroup result = layerGroupService.save(layerGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, layerGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /layer-groups : get all the layerGroups.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of layerGroups in body
     */
    @GetMapping("/layer-groups")
    @Timed
    public ResponseEntity<List<LayerGroup>> getAllLayerGroups(LayerGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LayerGroups by criteria: {}", criteria);
        Page<LayerGroup> page = layerGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/layer-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /layer-groups/:id : get the "id" layerGroup.
     *
     * @param id the id of the layerGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the layerGroup, or with status 404 (Not Found)
     */
    @GetMapping("/layer-groups/{id}")
    @Timed
    public ResponseEntity<LayerGroup> getLayerGroup(@PathVariable Long id) {
        log.debug("REST request to get LayerGroup : {}", id);
        LayerGroup layerGroup = layerGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(layerGroup));
    }

    /**
     * DELETE  /layer-groups/:id : delete the "id" layerGroup.
     *
     * @param id the id of the layerGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/layer-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteLayerGroup(@PathVariable Long id) {
        log.debug("REST request to delete LayerGroup : {}", id);
        layerGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
