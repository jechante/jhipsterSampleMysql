package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleMysqlApp;

import io.github.jhipster.application.domain.LayerGroup;
import io.github.jhipster.application.repository.LayerGroupRepository;
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

/**
 * Test class for the LayerGroupResource REST controller.
 *
 * @see LayerGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleMysqlApp.class)
public class LayerGroupResourceIntTest {

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    @Autowired
    private LayerGroupRepository layerGroupRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLayerGroupMockMvc;

    private LayerGroup layerGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LayerGroupResource layerGroupResource = new LayerGroupResource(layerGroupRepository);
        this.restLayerGroupMockMvc = MockMvcBuilders.standaloneSetup(layerGroupResource)
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
    public static LayerGroup createEntity(EntityManager em) {
        LayerGroup layerGroup = new LayerGroup()
            .groupName(DEFAULT_GROUP_NAME);
        return layerGroup;
    }

    @Before
    public void initTest() {
        layerGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createLayerGroup() throws Exception {
        int databaseSizeBeforeCreate = layerGroupRepository.findAll().size();

        // Create the LayerGroup
        restLayerGroupMockMvc.perform(post("/api/layer-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layerGroup)))
            .andExpect(status().isCreated());

        // Validate the LayerGroup in the database
        List<LayerGroup> layerGroupList = layerGroupRepository.findAll();
        assertThat(layerGroupList).hasSize(databaseSizeBeforeCreate + 1);
        LayerGroup testLayerGroup = layerGroupList.get(layerGroupList.size() - 1);
        assertThat(testLayerGroup.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
    }

    @Test
    @Transactional
    public void createLayerGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = layerGroupRepository.findAll().size();

        // Create the LayerGroup with an existing ID
        layerGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLayerGroupMockMvc.perform(post("/api/layer-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layerGroup)))
            .andExpect(status().isBadRequest());

        // Validate the LayerGroup in the database
        List<LayerGroup> layerGroupList = layerGroupRepository.findAll();
        assertThat(layerGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLayerGroups() throws Exception {
        // Initialize the database
        layerGroupRepository.saveAndFlush(layerGroup);

        // Get all the layerGroupList
        restLayerGroupMockMvc.perform(get("/api/layer-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(layerGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLayerGroup() throws Exception {
        // Initialize the database
        layerGroupRepository.saveAndFlush(layerGroup);

        // Get the layerGroup
        restLayerGroupMockMvc.perform(get("/api/layer-groups/{id}", layerGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(layerGroup.getId().intValue()))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLayerGroup() throws Exception {
        // Get the layerGroup
        restLayerGroupMockMvc.perform(get("/api/layer-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLayerGroup() throws Exception {
        // Initialize the database
        layerGroupRepository.saveAndFlush(layerGroup);
        int databaseSizeBeforeUpdate = layerGroupRepository.findAll().size();

        // Update the layerGroup
        LayerGroup updatedLayerGroup = layerGroupRepository.findOne(layerGroup.getId());
        // Disconnect from session so that the updates on updatedLayerGroup are not directly saved in db
        em.detach(updatedLayerGroup);
        updatedLayerGroup
            .groupName(UPDATED_GROUP_NAME);

        restLayerGroupMockMvc.perform(put("/api/layer-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLayerGroup)))
            .andExpect(status().isOk());

        // Validate the LayerGroup in the database
        List<LayerGroup> layerGroupList = layerGroupRepository.findAll();
        assertThat(layerGroupList).hasSize(databaseSizeBeforeUpdate);
        LayerGroup testLayerGroup = layerGroupList.get(layerGroupList.size() - 1);
        assertThat(testLayerGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingLayerGroup() throws Exception {
        int databaseSizeBeforeUpdate = layerGroupRepository.findAll().size();

        // Create the LayerGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLayerGroupMockMvc.perform(put("/api/layer-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layerGroup)))
            .andExpect(status().isCreated());

        // Validate the LayerGroup in the database
        List<LayerGroup> layerGroupList = layerGroupRepository.findAll();
        assertThat(layerGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLayerGroup() throws Exception {
        // Initialize the database
        layerGroupRepository.saveAndFlush(layerGroup);
        int databaseSizeBeforeDelete = layerGroupRepository.findAll().size();

        // Get the layerGroup
        restLayerGroupMockMvc.perform(delete("/api/layer-groups/{id}", layerGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LayerGroup> layerGroupList = layerGroupRepository.findAll();
        assertThat(layerGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LayerGroup.class);
        LayerGroup layerGroup1 = new LayerGroup();
        layerGroup1.setId(1L);
        LayerGroup layerGroup2 = new LayerGroup();
        layerGroup2.setId(layerGroup1.getId());
        assertThat(layerGroup1).isEqualTo(layerGroup2);
        layerGroup2.setId(2L);
        assertThat(layerGroup1).isNotEqualTo(layerGroup2);
        layerGroup1.setId(null);
        assertThat(layerGroup1).isNotEqualTo(layerGroup2);
    }
}
