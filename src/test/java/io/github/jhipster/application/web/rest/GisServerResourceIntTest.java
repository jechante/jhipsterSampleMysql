package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleMysqlApp;

import io.github.jhipster.application.domain.GisServer;
import io.github.jhipster.application.repository.GisServerRepository;
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

import io.github.jhipster.application.domain.enumeration.ServerVendor;
/**
 * Test class for the GisServerResource REST controller.
 *
 * @see GisServerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleMysqlApp.class)
public class GisServerResourceIntTest {

    private static final String DEFAULT_SERVER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final ServerVendor DEFAULT_VENDOR = ServerVendor.GEOSERVER;
    private static final ServerVendor UPDATED_VENDOR = ServerVendor.ARCGIS;

    @Autowired
    private GisServerRepository gisServerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGisServerMockMvc;

    private GisServer gisServer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GisServerResource gisServerResource = new GisServerResource(gisServerRepository);
        this.restGisServerMockMvc = MockMvcBuilders.standaloneSetup(gisServerResource)
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
    public static GisServer createEntity(EntityManager em) {
        GisServer gisServer = new GisServer()
            .serverName(DEFAULT_SERVER_NAME)
            .url(DEFAULT_URL)
            .vendor(DEFAULT_VENDOR);
        return gisServer;
    }

    @Before
    public void initTest() {
        gisServer = createEntity(em);
    }

    @Test
    @Transactional
    public void createGisServer() throws Exception {
        int databaseSizeBeforeCreate = gisServerRepository.findAll().size();

        // Create the GisServer
        restGisServerMockMvc.perform(post("/api/gis-servers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gisServer)))
            .andExpect(status().isCreated());

        // Validate the GisServer in the database
        List<GisServer> gisServerList = gisServerRepository.findAll();
        assertThat(gisServerList).hasSize(databaseSizeBeforeCreate + 1);
        GisServer testGisServer = gisServerList.get(gisServerList.size() - 1);
        assertThat(testGisServer.getServerName()).isEqualTo(DEFAULT_SERVER_NAME);
        assertThat(testGisServer.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testGisServer.getVendor()).isEqualTo(DEFAULT_VENDOR);
    }

    @Test
    @Transactional
    public void createGisServerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gisServerRepository.findAll().size();

        // Create the GisServer with an existing ID
        gisServer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGisServerMockMvc.perform(post("/api/gis-servers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gisServer)))
            .andExpect(status().isBadRequest());

        // Validate the GisServer in the database
        List<GisServer> gisServerList = gisServerRepository.findAll();
        assertThat(gisServerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGisServers() throws Exception {
        // Initialize the database
        gisServerRepository.saveAndFlush(gisServer);

        // Get all the gisServerList
        restGisServerMockMvc.perform(get("/api/gis-servers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gisServer.getId().intValue())))
            .andExpect(jsonPath("$.[*].serverName").value(hasItem(DEFAULT_SERVER_NAME.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].vendor").value(hasItem(DEFAULT_VENDOR.toString())));
    }

    @Test
    @Transactional
    public void getGisServer() throws Exception {
        // Initialize the database
        gisServerRepository.saveAndFlush(gisServer);

        // Get the gisServer
        restGisServerMockMvc.perform(get("/api/gis-servers/{id}", gisServer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gisServer.getId().intValue()))
            .andExpect(jsonPath("$.serverName").value(DEFAULT_SERVER_NAME.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.vendor").value(DEFAULT_VENDOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGisServer() throws Exception {
        // Get the gisServer
        restGisServerMockMvc.perform(get("/api/gis-servers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGisServer() throws Exception {
        // Initialize the database
        gisServerRepository.saveAndFlush(gisServer);
        int databaseSizeBeforeUpdate = gisServerRepository.findAll().size();

        // Update the gisServer
        GisServer updatedGisServer = gisServerRepository.findOne(gisServer.getId());
        // Disconnect from session so that the updates on updatedGisServer are not directly saved in db
        em.detach(updatedGisServer);
        updatedGisServer
            .serverName(UPDATED_SERVER_NAME)
            .url(UPDATED_URL)
            .vendor(UPDATED_VENDOR);

        restGisServerMockMvc.perform(put("/api/gis-servers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGisServer)))
            .andExpect(status().isOk());

        // Validate the GisServer in the database
        List<GisServer> gisServerList = gisServerRepository.findAll();
        assertThat(gisServerList).hasSize(databaseSizeBeforeUpdate);
        GisServer testGisServer = gisServerList.get(gisServerList.size() - 1);
        assertThat(testGisServer.getServerName()).isEqualTo(UPDATED_SERVER_NAME);
        assertThat(testGisServer.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testGisServer.getVendor()).isEqualTo(UPDATED_VENDOR);
    }

    @Test
    @Transactional
    public void updateNonExistingGisServer() throws Exception {
        int databaseSizeBeforeUpdate = gisServerRepository.findAll().size();

        // Create the GisServer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGisServerMockMvc.perform(put("/api/gis-servers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gisServer)))
            .andExpect(status().isCreated());

        // Validate the GisServer in the database
        List<GisServer> gisServerList = gisServerRepository.findAll();
        assertThat(gisServerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGisServer() throws Exception {
        // Initialize the database
        gisServerRepository.saveAndFlush(gisServer);
        int databaseSizeBeforeDelete = gisServerRepository.findAll().size();

        // Get the gisServer
        restGisServerMockMvc.perform(delete("/api/gis-servers/{id}", gisServer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GisServer> gisServerList = gisServerRepository.findAll();
        assertThat(gisServerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GisServer.class);
        GisServer gisServer1 = new GisServer();
        gisServer1.setId(1L);
        GisServer gisServer2 = new GisServer();
        gisServer2.setId(gisServer1.getId());
        assertThat(gisServer1).isEqualTo(gisServer2);
        gisServer2.setId(2L);
        assertThat(gisServer1).isNotEqualTo(gisServer2);
        gisServer1.setId(null);
        assertThat(gisServer1).isNotEqualTo(gisServer2);
    }
}
