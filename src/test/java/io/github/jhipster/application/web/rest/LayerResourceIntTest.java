package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleMysqlApp;

import io.github.jhipster.application.domain.Layer;
import io.github.jhipster.application.domain.Legend;
import io.github.jhipster.application.domain.GisServer;
import io.github.jhipster.application.domain.LayerGroup;
import io.github.jhipster.application.repository.LayerRepository;
import io.github.jhipster.application.service.LayerService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.LayerCriteria;
import io.github.jhipster.application.service.LayerQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.ServerType;
import io.github.jhipster.application.domain.enumeration.PointQueryType;
import io.github.jhipster.application.domain.enumeration.PoiType;
/**
 * Test class for the LayerResource REST controller.
 *
 * @see LayerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleMysqlApp.class)
public class LayerResourceIntTest {

    private static final String DEFAULT_LAYER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAYER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final ServerType DEFAULT_SOURCE = ServerType.OGC_WMS;
    private static final ServerType UPDATED_SOURCE = ServerType.OGC_WFS;

    private static final PointQueryType DEFAULT_POINT_QUERY_TYPE = PointQueryType.WMS_GETFEATUREINFO;
    private static final PointQueryType UPDATED_POINT_QUERY_TYPE = PointQueryType.WFS_CQL;

    private static final String DEFAULT_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_STYLE = "BBBBBBBBBB";

    private static final PoiType DEFAULT_POI_TYPE = PoiType.XY;
    private static final PoiType UPDATED_POI_TYPE = PoiType.WFS;

    private static final String DEFAULT_POI_URL = "AAAAAAAAAA";
    private static final String UPDATED_POI_URL = "BBBBBBBBBB";

    @Autowired
    private LayerRepository layerRepository;

    @Autowired
    private LayerService layerService;

    @Autowired
    private LayerQueryService layerQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLayerMockMvc;

    private Layer layer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LayerResource layerResource = new LayerResource(layerService, layerQueryService);
        this.restLayerMockMvc = MockMvcBuilders.standaloneSetup(layerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Layer createEntity(EntityManager em) {
        Layer layer = new Layer()
            .layerName(DEFAULT_LAYER_NAME)
            .identifier(DEFAULT_IDENTIFIER)
            .source(DEFAULT_SOURCE)
            .pointQueryType(DEFAULT_POINT_QUERY_TYPE)
            .style(DEFAULT_STYLE)
            .poiType(DEFAULT_POI_TYPE)
            .poiURL(DEFAULT_POI_URL);
        return layer;
    }

    @Before
    public void initTest() {
        layer = createEntity(em);
    }

    @Test
    @Transactional
    public void createLayer() throws Exception {
        int databaseSizeBeforeCreate = layerRepository.findAll().size();

        // Create the Layer
        restLayerMockMvc.perform(post("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layer)))
            .andExpect(status().isCreated());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeCreate + 1);
        Layer testLayer = layerList.get(layerList.size() - 1);
        assertThat(testLayer.getLayerName()).isEqualTo(DEFAULT_LAYER_NAME);
        assertThat(testLayer.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testLayer.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testLayer.getPointQueryType()).isEqualTo(DEFAULT_POINT_QUERY_TYPE);
        assertThat(testLayer.getStyle()).isEqualTo(DEFAULT_STYLE);
        assertThat(testLayer.getPoiType()).isEqualTo(DEFAULT_POI_TYPE);
        assertThat(testLayer.getPoiURL()).isEqualTo(DEFAULT_POI_URL);
    }

    @Test
    @Transactional
    public void createLayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = layerRepository.findAll().size();

        // Create the Layer with an existing ID
        layer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLayerMockMvc.perform(post("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layer)))
            .andExpect(status().isBadRequest());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLayers() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList
        restLayerMockMvc.perform(get("/api/layers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(layer.getId().intValue())))
            .andExpect(jsonPath("$.[*].layerName").value(hasItem(DEFAULT_LAYER_NAME.toString())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].pointQueryType").value(hasItem(DEFAULT_POINT_QUERY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE.toString())))
            .andExpect(jsonPath("$.[*].poiType").value(hasItem(DEFAULT_POI_TYPE.toString())))
            .andExpect(jsonPath("$.[*].poiURL").value(hasItem(DEFAULT_POI_URL.toString())));
    }

    @Test
    @Transactional
    public void getLayer() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get the layer
        restLayerMockMvc.perform(get("/api/layers/{id}", layer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(layer.getId().intValue()))
            .andExpect(jsonPath("$.layerName").value(DEFAULT_LAYER_NAME.toString()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.pointQueryType").value(DEFAULT_POINT_QUERY_TYPE.toString()))
            .andExpect(jsonPath("$.style").value(DEFAULT_STYLE.toString()))
            .andExpect(jsonPath("$.poiType").value(DEFAULT_POI_TYPE.toString()))
            .andExpect(jsonPath("$.poiURL").value(DEFAULT_POI_URL.toString()));
    }

    @Test
    @Transactional
    public void getAllLayersByLayerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where layerName equals to DEFAULT_LAYER_NAME
        defaultLayerShouldBeFound("layerName.equals=" + DEFAULT_LAYER_NAME);

        // Get all the layerList where layerName equals to UPDATED_LAYER_NAME
        defaultLayerShouldNotBeFound("layerName.equals=" + UPDATED_LAYER_NAME);
    }

    @Test
    @Transactional
    public void getAllLayersByLayerNameIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where layerName in DEFAULT_LAYER_NAME or UPDATED_LAYER_NAME
        defaultLayerShouldBeFound("layerName.in=" + DEFAULT_LAYER_NAME + "," + UPDATED_LAYER_NAME);

        // Get all the layerList where layerName equals to UPDATED_LAYER_NAME
        defaultLayerShouldNotBeFound("layerName.in=" + UPDATED_LAYER_NAME);
    }

    @Test
    @Transactional
    public void getAllLayersByLayerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where layerName is not null
        defaultLayerShouldBeFound("layerName.specified=true");

        // Get all the layerList where layerName is null
        defaultLayerShouldNotBeFound("layerName.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByIdentifierIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where identifier equals to DEFAULT_IDENTIFIER
        defaultLayerShouldBeFound("identifier.equals=" + DEFAULT_IDENTIFIER);

        // Get all the layerList where identifier equals to UPDATED_IDENTIFIER
        defaultLayerShouldNotBeFound("identifier.equals=" + UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllLayersByIdentifierIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where identifier in DEFAULT_IDENTIFIER or UPDATED_IDENTIFIER
        defaultLayerShouldBeFound("identifier.in=" + DEFAULT_IDENTIFIER + "," + UPDATED_IDENTIFIER);

        // Get all the layerList where identifier equals to UPDATED_IDENTIFIER
        defaultLayerShouldNotBeFound("identifier.in=" + UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllLayersByIdentifierIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where identifier is not null
        defaultLayerShouldBeFound("identifier.specified=true");

        // Get all the layerList where identifier is null
        defaultLayerShouldNotBeFound("identifier.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where source equals to DEFAULT_SOURCE
        defaultLayerShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the layerList where source equals to UPDATED_SOURCE
        defaultLayerShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllLayersBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultLayerShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the layerList where source equals to UPDATED_SOURCE
        defaultLayerShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllLayersBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where source is not null
        defaultLayerShouldBeFound("source.specified=true");

        // Get all the layerList where source is null
        defaultLayerShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByPointQueryTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where pointQueryType equals to DEFAULT_POINT_QUERY_TYPE
        defaultLayerShouldBeFound("pointQueryType.equals=" + DEFAULT_POINT_QUERY_TYPE);

        // Get all the layerList where pointQueryType equals to UPDATED_POINT_QUERY_TYPE
        defaultLayerShouldNotBeFound("pointQueryType.equals=" + UPDATED_POINT_QUERY_TYPE);
    }

    @Test
    @Transactional
    public void getAllLayersByPointQueryTypeIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where pointQueryType in DEFAULT_POINT_QUERY_TYPE or UPDATED_POINT_QUERY_TYPE
        defaultLayerShouldBeFound("pointQueryType.in=" + DEFAULT_POINT_QUERY_TYPE + "," + UPDATED_POINT_QUERY_TYPE);

        // Get all the layerList where pointQueryType equals to UPDATED_POINT_QUERY_TYPE
        defaultLayerShouldNotBeFound("pointQueryType.in=" + UPDATED_POINT_QUERY_TYPE);
    }

    @Test
    @Transactional
    public void getAllLayersByPointQueryTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where pointQueryType is not null
        defaultLayerShouldBeFound("pointQueryType.specified=true");

        // Get all the layerList where pointQueryType is null
        defaultLayerShouldNotBeFound("pointQueryType.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByStyleIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where style equals to DEFAULT_STYLE
        defaultLayerShouldBeFound("style.equals=" + DEFAULT_STYLE);

        // Get all the layerList where style equals to UPDATED_STYLE
        defaultLayerShouldNotBeFound("style.equals=" + UPDATED_STYLE);
    }

    @Test
    @Transactional
    public void getAllLayersByStyleIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where style in DEFAULT_STYLE or UPDATED_STYLE
        defaultLayerShouldBeFound("style.in=" + DEFAULT_STYLE + "," + UPDATED_STYLE);

        // Get all the layerList where style equals to UPDATED_STYLE
        defaultLayerShouldNotBeFound("style.in=" + UPDATED_STYLE);
    }

    @Test
    @Transactional
    public void getAllLayersByStyleIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where style is not null
        defaultLayerShouldBeFound("style.specified=true");

        // Get all the layerList where style is null
        defaultLayerShouldNotBeFound("style.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByPoiTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where poiType equals to DEFAULT_POI_TYPE
        defaultLayerShouldBeFound("poiType.equals=" + DEFAULT_POI_TYPE);

        // Get all the layerList where poiType equals to UPDATED_POI_TYPE
        defaultLayerShouldNotBeFound("poiType.equals=" + UPDATED_POI_TYPE);
    }

    @Test
    @Transactional
    public void getAllLayersByPoiTypeIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where poiType in DEFAULT_POI_TYPE or UPDATED_POI_TYPE
        defaultLayerShouldBeFound("poiType.in=" + DEFAULT_POI_TYPE + "," + UPDATED_POI_TYPE);

        // Get all the layerList where poiType equals to UPDATED_POI_TYPE
        defaultLayerShouldNotBeFound("poiType.in=" + UPDATED_POI_TYPE);
    }

    @Test
    @Transactional
    public void getAllLayersByPoiTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where poiType is not null
        defaultLayerShouldBeFound("poiType.specified=true");

        // Get all the layerList where poiType is null
        defaultLayerShouldNotBeFound("poiType.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByPoiURLIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where poiURL equals to DEFAULT_POI_URL
        defaultLayerShouldBeFound("poiURL.equals=" + DEFAULT_POI_URL);

        // Get all the layerList where poiURL equals to UPDATED_POI_URL
        defaultLayerShouldNotBeFound("poiURL.equals=" + UPDATED_POI_URL);
    }

    @Test
    @Transactional
    public void getAllLayersByPoiURLIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where poiURL in DEFAULT_POI_URL or UPDATED_POI_URL
        defaultLayerShouldBeFound("poiURL.in=" + DEFAULT_POI_URL + "," + UPDATED_POI_URL);

        // Get all the layerList where poiURL equals to UPDATED_POI_URL
        defaultLayerShouldNotBeFound("poiURL.in=" + UPDATED_POI_URL);
    }

    @Test
    @Transactional
    public void getAllLayersByPoiURLIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where poiURL is not null
        defaultLayerShouldBeFound("poiURL.specified=true");

        // Get all the layerList where poiURL is null
        defaultLayerShouldNotBeFound("poiURL.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByLegendsIsEqualToSomething() throws Exception {
        // Initialize the database
        Legend legends = LegendResourceIntTest.createEntity(em);
        em.persist(legends);
        em.flush();
        layer.addLegends(legends);
        layerRepository.saveAndFlush(layer);
        Long legendsId = legends.getId();

        // Get all the layerList where legends equals to legendsId
        defaultLayerShouldBeFound("legendsId.equals=" + legendsId);

        // Get all the layerList where legends equals to legendsId + 1
        defaultLayerShouldNotBeFound("legendsId.equals=" + (legendsId + 1));
    }


    @Test
    @Transactional
    public void getAllLayersByGisServerIsEqualToSomething() throws Exception {
        // Initialize the database
        GisServer gisServer = GisServerResourceIntTest.createEntity(em);
        em.persist(gisServer);
        em.flush();
        layer.setGisServer(gisServer);
        layerRepository.saveAndFlush(layer);
        Long gisServerId = gisServer.getId();

        // Get all the layerList where gisServer equals to gisServerId
        defaultLayerShouldBeFound("gisServerId.equals=" + gisServerId);

        // Get all the layerList where gisServer equals to gisServerId + 1
        defaultLayerShouldNotBeFound("gisServerId.equals=" + (gisServerId + 1));
    }


    @Test
    @Transactional
    public void getAllLayersByLayerGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        LayerGroup layerGroup = LayerGroupResourceIntTest.createEntity(em);
        em.persist(layerGroup);
        em.flush();
        layer.setLayerGroup(layerGroup);
        layerRepository.saveAndFlush(layer);
        Long layerGroupId = layerGroup.getId();

        // Get all the layerList where layerGroup equals to layerGroupId
        defaultLayerShouldBeFound("layerGroupId.equals=" + layerGroupId);

        // Get all the layerList where layerGroup equals to layerGroupId + 1
        defaultLayerShouldNotBeFound("layerGroupId.equals=" + (layerGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLayerShouldBeFound(String filter) throws Exception {
        restLayerMockMvc.perform(get("/api/layers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(layer.getId().intValue())))
            .andExpect(jsonPath("$.[*].layerName").value(hasItem(DEFAULT_LAYER_NAME.toString())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].pointQueryType").value(hasItem(DEFAULT_POINT_QUERY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE.toString())))
            .andExpect(jsonPath("$.[*].poiType").value(hasItem(DEFAULT_POI_TYPE.toString())))
            .andExpect(jsonPath("$.[*].poiURL").value(hasItem(DEFAULT_POI_URL.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLayerShouldNotBeFound(String filter) throws Exception {
        restLayerMockMvc.perform(get("/api/layers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingLayer() throws Exception {
        // Get the layer
        restLayerMockMvc.perform(get("/api/layers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLayer() throws Exception {
        // Initialize the database
        layerService.save(layer);

        int databaseSizeBeforeUpdate = layerRepository.findAll().size();

        // Update the layer
        Layer updatedLayer = layerRepository.findOne(layer.getId());
        // Disconnect from session so that the updates on updatedLayer are not directly saved in db
        em.detach(updatedLayer);
        updatedLayer
            .layerName(UPDATED_LAYER_NAME)
            .identifier(UPDATED_IDENTIFIER)
            .source(UPDATED_SOURCE)
            .pointQueryType(UPDATED_POINT_QUERY_TYPE)
            .style(UPDATED_STYLE)
            .poiType(UPDATED_POI_TYPE)
            .poiURL(UPDATED_POI_URL);

        restLayerMockMvc.perform(put("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLayer)))
            .andExpect(status().isOk());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeUpdate);
        Layer testLayer = layerList.get(layerList.size() - 1);
        assertThat(testLayer.getLayerName()).isEqualTo(UPDATED_LAYER_NAME);
        assertThat(testLayer.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testLayer.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testLayer.getPointQueryType()).isEqualTo(UPDATED_POINT_QUERY_TYPE);
        assertThat(testLayer.getStyle()).isEqualTo(UPDATED_STYLE);
        assertThat(testLayer.getPoiType()).isEqualTo(UPDATED_POI_TYPE);
        assertThat(testLayer.getPoiURL()).isEqualTo(UPDATED_POI_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingLayer() throws Exception {
        int databaseSizeBeforeUpdate = layerRepository.findAll().size();

        // Create the Layer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLayerMockMvc.perform(put("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layer)))
            .andExpect(status().isCreated());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLayer() throws Exception {
        // Initialize the database
        layerService.save(layer);

        int databaseSizeBeforeDelete = layerRepository.findAll().size();

        // Get the layer
        restLayerMockMvc.perform(delete("/api/layers/{id}", layer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Layer.class);
        Layer layer1 = new Layer();
        layer1.setId(1L);
        Layer layer2 = new Layer();
        layer2.setId(layer1.getId());
        assertThat(layer1).isEqualTo(layer2);
        layer2.setId(2L);
        assertThat(layer1).isNotEqualTo(layer2);
        layer1.setId(null);
        assertThat(layer1).isNotEqualTo(layer2);
    }
}
