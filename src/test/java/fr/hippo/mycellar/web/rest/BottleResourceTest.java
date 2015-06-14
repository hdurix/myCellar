package fr.hippo.mycellar.web.rest;

import fr.hippo.mycellar.Application;
import fr.hippo.mycellar.domain.Bottle;
import fr.hippo.mycellar.repository.BottleRepository;

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
 * Test class for the BottleResource REST controller.
 *
 * @see BottleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BottleResourceTest {


    private static final Integer DEFAULT_YEAR = 0;
    private static final Integer UPDATED_YEAR = 1;

    private static final Long DEFAULT_PRICE = 0L;
    private static final Long UPDATED_PRICE = 1L;
    private static final String DEFAULT_IMAGE = "SAMPLE_TEXT";
    private static final String UPDATED_IMAGE = "UPDATED_TEXT";

    @Inject
    private BottleRepository bottleRepository;

    private MockMvc restBottleMockMvc;

    private Bottle bottle;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BottleResource bottleResource = new BottleResource();
        ReflectionTestUtils.setField(bottleResource, "bottleRepository", bottleRepository);
        this.restBottleMockMvc = MockMvcBuilders.standaloneSetup(bottleResource).build();
    }

    @Before
    public void initTest() {
        bottle = new Bottle();
        bottle.setYear(DEFAULT_YEAR);
        bottle.setPrice(DEFAULT_PRICE);
        bottle.setImage(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void createBottle() throws Exception {
        int databaseSizeBeforeCreate = bottleRepository.findAll().size();

        // Create the Bottle
        restBottleMockMvc.perform(post("/api/bottles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bottle)))
                .andExpect(status().isCreated());

        // Validate the Bottle in the database
        List<Bottle> bottles = bottleRepository.findAll();
        assertThat(bottles).hasSize(databaseSizeBeforeCreate + 1);
        Bottle testBottle = bottles.get(bottles.size() - 1);
        assertThat(testBottle.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testBottle.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBottle.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bottleRepository.findAll()).hasSize(0);
        // set the field null
        bottle.setYear(null);

        // Create the Bottle, which fails.
        restBottleMockMvc.perform(post("/api/bottles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bottle)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bottle> bottles = bottleRepository.findAll();
        assertThat(bottles).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllBottles() throws Exception {
        // Initialize the database
        bottleRepository.saveAndFlush(bottle);

        // Get all the bottles
        restBottleMockMvc.perform(get("/api/bottles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bottle.getId().intValue())))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getBottle() throws Exception {
        // Initialize the database
        bottleRepository.saveAndFlush(bottle);

        // Get the bottle
        restBottleMockMvc.perform(get("/api/bottles/{id}", bottle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bottle.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBottle() throws Exception {
        // Get the bottle
        restBottleMockMvc.perform(get("/api/bottles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBottle() throws Exception {
        // Initialize the database
        bottleRepository.saveAndFlush(bottle);

		int databaseSizeBeforeUpdate = bottleRepository.findAll().size();

        // Update the bottle
        bottle.setYear(UPDATED_YEAR);
        bottle.setPrice(UPDATED_PRICE);
        bottle.setImage(UPDATED_IMAGE);
        restBottleMockMvc.perform(put("/api/bottles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bottle)))
                .andExpect(status().isOk());

        // Validate the Bottle in the database
        List<Bottle> bottles = bottleRepository.findAll();
        assertThat(bottles).hasSize(databaseSizeBeforeUpdate);
        Bottle testBottle = bottles.get(bottles.size() - 1);
        assertThat(testBottle.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testBottle.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBottle.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void deleteBottle() throws Exception {
        // Initialize the database
        bottleRepository.saveAndFlush(bottle);

		int databaseSizeBeforeDelete = bottleRepository.findAll().size();

        // Get the bottle
        restBottleMockMvc.perform(delete("/api/bottles/{id}", bottle.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bottle> bottles = bottleRepository.findAll();
        assertThat(bottles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
