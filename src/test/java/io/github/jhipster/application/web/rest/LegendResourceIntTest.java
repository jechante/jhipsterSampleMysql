package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleMysqlApp;

import io.github.jhipster.application.domain.Legend;
import io.github.jhipster.application.repository.LegendRepository;
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
 * Test class for the LegendResource REST controller.
 *
 * @see LegendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleMysqlApp.class)
public class LegendResourceIntTest {

    private static final String DEFAULT_LEGEND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LEGEND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    @Autowired
    private LegendRepository legendRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLegendMockMvc;

    private Legend legend;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LegendResource legendResource = new LegendResource(legendRepository);
        this.restLegendMockMvc = MockMvcBuilders.standaloneSetup(legendResource)
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
    public static Legend createEntity(EntityManager em) {
        Legend legend = new Legend()
            .legendName(DEFAULT_LEGEND_NAME)
            .logo(DEFAULT_LOGO);
        return legend;
    }

    @Before
    public void initTest() {
        legend = createEntity(em);
    }

    @Test
    @Transactional
    public void createLegend() throws Exception {
        int databaseSizeBeforeCreate = legendRepository.findAll().size();

        // Create the Legend
        restLegendMockMvc.perform(post("/api/legends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legend)))
            .andExpect(status().isCreated());

        // Validate the Legend in the database
        List<Legend> legendList = legendRepository.findAll();
        assertThat(legendList).hasSize(databaseSizeBeforeCreate + 1);
        Legend testLegend = legendList.get(legendList.size() - 1);
        assertThat(testLegend.getLegendName()).isEqualTo(DEFAULT_LEGEND_NAME);
        assertThat(testLegend.getLogo()).isEqualTo(DEFAULT_LOGO);
    }

    @Test
    @Transactional
    public void createLegendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = legendRepository.findAll().size();

        // Create the Legend with an existing ID
        legend.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLegendMockMvc.perform(post("/api/legends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legend)))
            .andExpect(status().isBadRequest());

        // Validate the Legend in the database
        List<Legend> legendList = legendRepository.findAll();
        assertThat(legendList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLegends() throws Exception {
        // Initialize the database
        legendRepository.saveAndFlush(legend);

        // Get all the legendList
        restLegendMockMvc.perform(get("/api/legends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(legend.getId().intValue())))
            .andExpect(jsonPath("$.[*].legendName").value(hasItem(DEFAULT_LEGEND_NAME.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.toString())));
    }

    @Test
    @Transactional
    public void getLegend() throws Exception {
        // Initialize the database
        legendRepository.saveAndFlush(legend);

        // Get the legend
        restLegendMockMvc.perform(get("/api/legends/{id}", legend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(legend.getId().intValue()))
            .andExpect(jsonPath("$.legendName").value(DEFAULT_LEGEND_NAME.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLegend() throws Exception {
        // Get the legend
        restLegendMockMvc.perform(get("/api/legends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLegend() throws Exception {
        // Initialize the database
        legendRepository.saveAndFlush(legend);
        int databaseSizeBeforeUpdate = legendRepository.findAll().size();

        // Update the legend
        Legend updatedLegend = legendRepository.findOne(legend.getId());
        // Disconnect from session so that the updates on updatedLegend are not directly saved in db
        em.detach(updatedLegend);
        updatedLegend
            .legendName(UPDATED_LEGEND_NAME)
            .logo(UPDATED_LOGO);

        restLegendMockMvc.perform(put("/api/legends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLegend)))
            .andExpect(status().isOk());

        // Validate the Legend in the database
        List<Legend> legendList = legendRepository.findAll();
        assertThat(legendList).hasSize(databaseSizeBeforeUpdate);
        Legend testLegend = legendList.get(legendList.size() - 1);
        assertThat(testLegend.getLegendName()).isEqualTo(UPDATED_LEGEND_NAME);
        assertThat(testLegend.getLogo()).isEqualTo(UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void updateNonExistingLegend() throws Exception {
        int databaseSizeBeforeUpdate = legendRepository.findAll().size();

        // Create the Legend

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLegendMockMvc.perform(put("/api/legends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legend)))
            .andExpect(status().isCreated());

        // Validate the Legend in the database
        List<Legend> legendList = legendRepository.findAll();
        assertThat(legendList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLegend() throws Exception {
        // Initialize the database
        legendRepository.saveAndFlush(legend);
        int databaseSizeBeforeDelete = legendRepository.findAll().size();

        // Get the legend
        restLegendMockMvc.perform(delete("/api/legends/{id}", legend.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Legend> legendList = legendRepository.findAll();
        assertThat(legendList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Legend.class);
        Legend legend1 = new Legend();
        legend1.setId(1L);
        Legend legend2 = new Legend();
        legend2.setId(legend1.getId());
        assertThat(legend1).isEqualTo(legend2);
        legend2.setId(2L);
        assertThat(legend1).isNotEqualTo(legend2);
        legend1.setId(null);
        assertThat(legend1).isNotEqualTo(legend2);
    }
}
