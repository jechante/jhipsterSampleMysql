package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.GisServer;

import io.github.jhipster.application.repository.GisServerRepository;
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
 * REST controller for managing GisServer.
 */
@RestController
@RequestMapping("/api")
public class GisServerResource {

    private final Logger log = LoggerFactory.getLogger(GisServerResource.class);

    private static final String ENTITY_NAME = "gisServer";

    private final GisServerRepository gisServerRepository;

    public GisServerResource(GisServerRepository gisServerRepository) {
        this.gisServerRepository = gisServerRepository;
    }

    /**
     * POST  /gis-servers : Create a new gisServer.
     *
     * @param gisServer the gisServer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gisServer, or with status 400 (Bad Request) if the gisServer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gis-servers")
    @Timed
    public ResponseEntity<GisServer> createGisServer(@RequestBody GisServer gisServer) throws URISyntaxException {
        log.debug("REST request to save GisServer : {}", gisServer);
        if (gisServer.getId() != null) {
            throw new BadRequestAlertException("A new gisServer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GisServer result = gisServerRepository.save(gisServer);
        return ResponseEntity.created(new URI("/api/gis-servers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gis-servers : Updates an existing gisServer.
     *
     * @param gisServer the gisServer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gisServer,
     * or with status 400 (Bad Request) if the gisServer is not valid,
     * or with status 500 (Internal Server Error) if the gisServer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gis-servers")
    @Timed
    public ResponseEntity<GisServer> updateGisServer(@RequestBody GisServer gisServer) throws URISyntaxException {
        log.debug("REST request to update GisServer : {}", gisServer);
        if (gisServer.getId() == null) {
            return createGisServer(gisServer);
        }
        GisServer result = gisServerRepository.save(gisServer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gisServer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gis-servers : get all the gisServers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gisServers in body
     */
    @GetMapping("/gis-servers")
    @Timed
    public ResponseEntity<List<GisServer>> getAllGisServers(Pageable pageable) {
        log.debug("REST request to get a page of GisServers");
        Page<GisServer> page = gisServerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gis-servers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /gis-servers/:id : get the "id" gisServer.
     *
     * @param id the id of the gisServer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gisServer, or with status 404 (Not Found)
     */
    @GetMapping("/gis-servers/{id}")
    @Timed
    public ResponseEntity<GisServer> getGisServer(@PathVariable Long id) {
        log.debug("REST request to get GisServer : {}", id);
        GisServer gisServer = gisServerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gisServer));
    }

    /**
     * DELETE  /gis-servers/:id : delete the "id" gisServer.
     *
     * @param id the id of the gisServer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gis-servers/{id}")
    @Timed
    public ResponseEntity<Void> deleteGisServer(@PathVariable Long id) {
        log.debug("REST request to delete GisServer : {}", id);
        gisServerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
