package fr.hippo.mycellar.web.rest;

import fr.hippo.mycellar.Application;
import fr.hippo.mycellar.domain.Vineward;
import fr.hippo.mycellar.repository.VinewardRepository;

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
 * Test class for the VinewardResource REST controller.
 *
 * @see VinewardResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VinewardResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_LOCATION = "SAMPLE_TEXT";
    private static final String UPDATED_LOCATION = "UPDATED_TEXT";

    @Inject
    private VinewardRepository vinewardRepository;

    private MockMvc restVinewardMockMvc;

    private Vineward vineward;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VinewardResource vinewardResource = new VinewardResource();
        ReflectionTestUtils.setField(vinewardResource, "vinewardRepository", vinewardRepository);
        this.restVinewardMockMvc = MockMvcBuilders.standaloneSetup(vinewardResource).build();
    }

    @Before
    public void initTest() {
        vineward = new Vineward();
        vineward.setName(DEFAULT_NAME);
        vineward.setLocation(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void createVineward() throws Exception {
        int databaseSizeBeforeCreate = vinewardRepository.findAll().size();

        // Create the Vineward
        restVinewardMockMvc.perform(post("/api/vinewards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vineward)))
                .andExpect(status().isCreated());

        // Validate the Vineward in the database
        List<Vineward> vinewards = vinewardRepository.findAll();
        assertThat(vinewards).hasSize(databaseSizeBeforeCreate + 1);
        Vineward testVineward = vinewards.get(vinewards.size() - 1);
        assertThat(testVineward.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVineward.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(vinewardRepository.findAll()).hasSize(0);
        // set the field null
        vineward.setName(null);

        // Create the Vineward, which fails.
        restVinewardMockMvc.perform(post("/api/vinewards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vineward)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Vineward> vinewards = vinewardRepository.findAll();
        assertThat(vinewards).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllVinewards() throws Exception {
        // Initialize the database
        vinewardRepository.saveAndFlush(vineward);

        // Get all the vinewards
        restVinewardMockMvc.perform(get("/api/vinewards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(vineward.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void getVineward() throws Exception {
        // Initialize the database
        vinewardRepository.saveAndFlush(vineward);

        // Get the vineward
        restVinewardMockMvc.perform(get("/api/vinewards/{id}", vineward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(vineward.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVineward() throws Exception {
        // Get the vineward
        restVinewardMockMvc.perform(get("/api/vinewards/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVineward() throws Exception {
        // Initialize the database
        vinewardRepository.saveAndFlush(vineward);

		int databaseSizeBeforeUpdate = vinewardRepository.findAll().size();

        // Update the vineward
        vineward.setName(UPDATED_NAME);
        vineward.setLocation(UPDATED_LOCATION);
        restVinewardMockMvc.perform(put("/api/vinewards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vineward)))
                .andExpect(status().isOk());

        // Validate the Vineward in the database
        List<Vineward> vinewards = vinewardRepository.findAll();
        assertThat(vinewards).hasSize(databaseSizeBeforeUpdate);
        Vineward testVineward = vinewards.get(vinewards.size() - 1);
        assertThat(testVineward.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVineward.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void deleteVineward() throws Exception {
        // Initialize the database
        vinewardRepository.saveAndFlush(vineward);

		int databaseSizeBeforeDelete = vinewardRepository.findAll().size();

        // Get the vineward
        restVinewardMockMvc.perform(delete("/api/vinewards/{id}", vineward.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Vineward> vinewards = vinewardRepository.findAll();
        assertThat(vinewards).hasSize(databaseSizeBeforeDelete - 1);
    }
}
