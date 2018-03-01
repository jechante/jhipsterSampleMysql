package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Layer;

import io.github.jhipster.application.repository.LayerRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
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
 * REST controller for managing Layer.
 */
@RestController
@RequestMapping("/api")
public class LayerResource {

    private final Logger log = LoggerFactory.getLogger(LayerResource.class);

    private static final String ENTITY_NAME = "layer";

    private final LayerRepository layerRepository;

    public LayerResource(LayerRepository layerRepository) {
        this.layerRepository = layerRepository;
    }

    /**
     * POST  /layers : Create a new layer.
     *
     * @param layer the layer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new layer, or with status 400 (Bad Request) if the layer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/layers")
    @Timed
    public ResponseEntity<Layer> createLayer(@RequestBody Layer layer) throws URISyntaxException {
        log.debug("REST request to save Layer : {}", layer);
        if (layer.getId() != null) {
            throw new BadRequestAlertException("A new layer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Layer result = layerRepository.save(layer);
        return ResponseEntity.created(new URI("/api/layers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /layers : Updates an existing layer.
     *
     * @param layer the layer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated layer,
     * or with status 400 (Bad Request) if the layer is not valid,
     * or with status 500 (Internal Server Error) if the layer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/layers")
    @Timed
    public ResponseEntity<Layer> updateLayer(@RequestBody Layer layer) throws URISyntaxException {
        log.debug("REST request to update Layer : {}", layer);
        if (layer.getId() == null) {
            return createLayer(layer);
        }
        Layer result = layerRepository.save(layer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, layer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /layers : get all the layers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of layers in body
     */
    @GetMapping("/layers")
    @Timed
    public ResponseEntity<List<Layer>> getAllLayers(Pageable pageable) {
        log.debug("REST request to get a page of Layers");
        Page<Layer> page = layerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/layers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /layers/:id : get the "id" layer.
     *
     * @param id the id of the layer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the layer, or with status 404 (Not Found)
     */
    @GetMapping("/layers/{id}")
    @Timed
    public ResponseEntity<Layer> getLayer(@PathVariable Long id) {
        log.debug("REST request to get Layer : {}", id);
        Layer layer = layerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(layer));
    }

    /**
     * DELETE  /layers/:id : delete the "id" layer.
     *
     * @param id the id of the layer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/layers/{id}")
    @Timed
    public ResponseEntity<Void> deleteLayer(@PathVariable Long id) {
        log.debug("REST request to delete Layer : {}", id);
        layerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
