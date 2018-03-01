package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleMysqlApp;

import io.github.jhipster.application.domain.LayerGroup;
import io.github.jhipster.application.domain.Layer;
import io.github.jhipster.application.domain.LayerGroup;
import io.github.jhipster.application.domain.LayerGroup;
import io.github.jhipster.application.repository.LayerGroupRepository;
import io.github.jhipster.application.service.LayerGroupService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.LayerGroupCriteria;
import io.github.jhipster.application.service.LayerGroupQueryService;

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
    private LayerGroupService layerGroupService;

    @Autowired
    private LayerGroupQueryService layerGroupQueryService;

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
        final LayerGroupResource layerGroupResource = new LayerGroupResource(layerGroupService, layerGroupQueryService);
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
    public void getAllLayerGroupsByGroupNameIsEqualToSomething() throws Exception {
        // Initialize the database
        layerGroupRepository.saveAndFlush(layerGroup);

        // Get all the layerGroupList where groupName equals to DEFAULT_GROUP_NAME
        defaultLayerGroupShouldBeFound("groupName.equals=" + DEFAULT_GROUP_NAME);

        // Get all the layerGroupList where groupName equals to UPDATED_GROUP_NAME
        defaultLayerGroupShouldNotBeFound("groupName.equals=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllLayerGroupsByGroupNameIsInShouldWork() throws Exception {
        // Initialize the database
        layerGroupRepository.saveAndFlush(layerGroup);

        // Get all the layerGroupList where groupName in DEFAULT_GROUP_NAME or UPDATED_GROUP_NAME
        defaultLayerGroupShouldBeFound("groupName.in=" + DEFAULT_GROUP_NAME + "," + UPDATED_GROUP_NAME);

        // Get all the layerGroupList where groupName equals to UPDATED_GROUP_NAME
        defaultLayerGroupShouldNotBeFound("groupName.in=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllLayerGroupsByGroupNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerGroupRepository.saveAndFlush(layerGroup);

        // Get all the layerGroupList where groupName is not null
        defaultLayerGroupShouldBeFound("groupName.specified=true");

        // Get all the layerGroupList where groupName is null
        defaultLayerGroupShouldNotBeFound("groupName.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayerGroupsByLayersIsEqualToSomething() throws Exception {
        // Initialize the database
        Layer layers = LayerResourceIntTest.createEntity(em);
        em.persist(layers);
        em.flush();
        layerGroup.addLayers(layers);
        layerGroupRepository.saveAndFlush(layerGroup);
        Long layersId = layers.getId();

        // Get all the layerGroupList where layers equals to layersId
        defaultLayerGroupShouldBeFound("layersId.equals=" + layersId);

        // Get all the layerGroupList where layers equals to layersId + 1
        defaultLayerGroupShouldNotBeFound("layersId.equals=" + (layersId + 1));
    }


    @Test
    @Transactional
    public void getAllLayerGroupsBySubGroupsIsEqualToSomething() throws Exception {
        // Initialize the database
        LayerGroup subGroups = LayerGroupResourceIntTest.createEntity(em);
        em.persist(subGroups);
        em.flush();
        layerGroup.addSubGroups(subGroups);
        layerGroupRepository.saveAndFlush(layerGroup);
        Long subGroupsId = subGroups.getId();

        // Get all the layerGroupList where subGroups equals to subGroupsId
        defaultLayerGroupShouldBeFound("subGroupsId.equals=" + subGroupsId);

        // Get all the layerGroupList where subGroups equals to subGroupsId + 1
        defaultLayerGroupShouldNotBeFound("subGroupsId.equals=" + (subGroupsId + 1));
    }


    @Test
    @Transactional
    public void getAllLayerGroupsByParentGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        LayerGroup parentGroup = LayerGroupResourceIntTest.createEntity(em);
        em.persist(parentGroup);
        em.flush();
        layerGroup.setParentGroup(parentGroup);
        layerGroupRepository.saveAndFlush(layerGroup);
        Long parentGroupId = parentGroup.getId();

        // Get all the layerGroupList where parentGroup equals to parentGroupId
        defaultLayerGroupShouldBeFound("parentGroupId.equals=" + parentGroupId);

        // Get all the layerGroupList where parentGroup equals to parentGroupId + 1
        defaultLayerGroupShouldNotBeFound("parentGroupId.equals=" + (parentGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLayerGroupShouldBeFound(String filter) throws Exception {
        restLayerGroupMockMvc.perform(get("/api/layer-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(layerGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLayerGroupShouldNotBeFound(String filter) throws Exception {
        restLayerGroupMockMvc.perform(get("/api/layer-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
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
        layerGroupService.save(layerGroup);

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
        layerGroupService.save(layerGroup);

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
