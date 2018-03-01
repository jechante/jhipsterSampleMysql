package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleMysqlApp;

import io.github.jhipster.application.domain.Layer;
import io.github.jhipster.application.repository.LayerRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

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
        final LayerResource layerResource = new LayerResource(layerRepository);
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
    public void getNonExistingLayer() throws Exception {
        // Get the layer
        restLayerMockMvc.perform(get("/api/layers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLayer() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);
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
        layerRepository.saveAndFlush(layer);
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
