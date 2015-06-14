package fr.hippo.mycellar.web.rest;

import fr.hippo.mycellar.Application;
import fr.hippo.mycellar.domain.Appellation;
import fr.hippo.mycellar.repository.AppellationRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AppellationResource REST controller.
 *
 * @see AppellationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AppellationResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    @Inject
    private AppellationRepository appellationRepository;

    private MockMvc restAppellationMockMvc;

    private Appellation appellation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppellationResource appellationResource = new AppellationResource();
        ReflectionTestUtils.setField(appellationResource, "appellationRepository", appellationRepository);
        this.restAppellationMockMvc = MockMvcBuilders.standaloneSetup(appellationResource).build();
    }

    @Before
    public void initTest() {
        appellation = new Appellation();
        appellation.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAppellation() throws Exception {
        int databaseSizeBeforeCreate = appellationRepository.findAll().size();

        // Create the Appellation
        restAppellationMockMvc.perform(post("/api/appellations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(appellation)))
                .andExpect(status().isCreated());

        // Validate the Appellation in the database
        List<Appellation> appellations = appellationRepository.findAll();
        assertThat(appellations).hasSize(databaseSizeBeforeCreate + 1);
        Appellation testAppellation = appellations.get(appellations.size() - 1);
        assertThat(testAppellation.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(appellationRepository.findAll()).hasSize(0);
        // set the field null
        appellation.setName(null);

        // Create the Appellation, which fails.
        restAppellationMockMvc.perform(post("/api/appellations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(appellation)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Appellation> appellations = appellationRepository.findAll();
        assertThat(appellations).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllAppellations() throws Exception {
        // Initialize the database
        appellationRepository.saveAndFlush(appellation);

        // Get all the appellations
        restAppellationMockMvc.perform(get("/api/appellations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(appellation.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAppellation() throws Exception {
        // Initialize the database
        appellationRepository.saveAndFlush(appellation);

        // Get the appellation
        restAppellationMockMvc.perform(get("/api/appellations/{id}", appellation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(appellation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppellation() throws Exception {
        // Get the appellation
        restAppellationMockMvc.perform(get("/api/appellations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppellation() throws Exception {
        // Initialize the database
        appellationRepository.saveAndFlush(appellation);

		int databaseSizeBeforeUpdate = appellationRepository.findAll().size();

        // Update the appellation
        appellation.setName(UPDATED_NAME);
        restAppellationMockMvc.perform(put("/api/appellations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(appellation)))
                .andExpect(status().isOk());

        // Validate the Appellation in the database
        List<Appellation> appellations = appellationRepository.findAll();
        assertThat(appellations).hasSize(databaseSizeBeforeUpdate);
        Appellation testAppellation = appellations.get(appellations.size() - 1);
        assertThat(testAppellation.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteAppellation() throws Exception {
        // Initialize the database
        appellationRepository.saveAndFlush(appellation);

		int databaseSizeBeforeDelete = appellationRepository.findAll().size();

        // Get the appellation
        restAppellationMockMvc.perform(delete("/api/appellations/{id}", appellation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Appellation> appellations = appellationRepository.findAll();
        assertThat(appellations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
