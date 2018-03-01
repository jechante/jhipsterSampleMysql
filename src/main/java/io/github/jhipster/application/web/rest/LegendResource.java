package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Legend;

import io.github.jhipster.application.repository.LegendRepository;
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
 * REST controller for managing Legend.
 */
@RestController
@RequestMapping("/api")
public class LegendResource {

    private final Logger log = LoggerFactory.getLogger(LegendResource.class);

    private static final String ENTITY_NAME = "legend";

    private final LegendRepository legendRepository;

    public LegendResource(LegendRepository legendRepository) {
        this.legendRepository = legendRepository;
    }

    /**
     * POST  /legends : Create a new legend.
     *
     * @param legend the legend to create
     * @return the ResponseEntity with status 201 (Created) and with body the new legend, or with status 400 (Bad Request) if the legend has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/legends")
    @Timed
    public ResponseEntity<Legend> createLegend(@RequestBody Legend legend) throws URISyntaxException {
        log.debug("REST request to save Legend : {}", legend);
        if (legend.getId() != null) {
            throw new BadRequestAlertException("A new legend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Legend result = legendRepository.save(legend);
        return ResponseEntity.created(new URI("/api/legends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /legends : Updates an existing legend.
     *
     * @param legend the legend to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated legend,
     * or with status 400 (Bad Request) if the legend is not valid,
     * or with status 500 (Internal Server Error) if the legend couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/legends")
    @Timed
    public ResponseEntity<Legend> updateLegend(@RequestBody Legend legend) throws URISyntaxException {
        log.debug("REST request to update Legend : {}", legend);
        if (legend.getId() == null) {
            return createLegend(legend);
        }
        Legend result = legendRepository.save(legend);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, legend.getId().toString()))
            .body(result);
    }

    /**
     * GET  /legends : get all the legends.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of legends in body
     */
    @GetMapping("/legends")
    @Timed
    public ResponseEntity<List<Legend>> getAllLegends(Pageable pageable) {
        log.debug("REST request to get a page of Legends");
        Page<Legend> page = legendRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/legends");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /legends/:id : get the "id" legend.
     *
     * @param id the id of the legend to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the legend, or with status 404 (Not Found)
     */
    @GetMapping("/legends/{id}")
    @Timed
    public ResponseEntity<Legend> getLegend(@PathVariable Long id) {
        log.debug("REST request to get Legend : {}", id);
        Legend legend = legendRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(legend));
    }

    /**
     * DELETE  /legends/:id : delete the "id" legend.
     *
     * @param id the id of the legend to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/legends/{id}")
    @Timed
    public ResponseEntity<Void> deleteLegend(@PathVariable Long id) {
        log.debug("REST request to delete Legend : {}", id);
        legendRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
