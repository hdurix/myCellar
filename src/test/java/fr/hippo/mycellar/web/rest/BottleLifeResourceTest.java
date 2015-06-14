package fr.hippo.mycellar.web.rest;

import fr.hippo.mycellar.Application;
import fr.hippo.mycellar.domain.BottleLife;
import fr.hippo.mycellar.repository.BottleLifeRepository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BottleLifeResource REST controller.
 *
 * @see BottleLifeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BottleLifeResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_BOUGHT_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_BOUGHT_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_BOUGHT_DATE_STR = dateTimeFormatter.print(DEFAULT_BOUGHT_DATE);

    private static final DateTime DEFAULT_DRINKED_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DRINKED_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DRINKED_DATE_STR = dateTimeFormatter.print(DEFAULT_DRINKED_DATE);

    @Inject
    private BottleLifeRepository bottleLifeRepository;

    private MockMvc restBottleLifeMockMvc;

    private BottleLife bottleLife;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BottleLifeResource bottleLifeResource = new BottleLifeResource();
        ReflectionTestUtils.setField(bottleLifeResource, "bottleLifeRepository", bottleLifeRepository);
        this.restBottleLifeMockMvc = MockMvcBuilders.standaloneSetup(bottleLifeResource).build();
    }

    @Before
    public void initTest() {
        bottleLife = new BottleLife();
        bottleLife.setBoughtDate(DEFAULT_BOUGHT_DATE);
        bottleLife.setDrinkedDate(DEFAULT_DRINKED_DATE);
    }

    @Test
    @Transactional
    public void createBottleLife() throws Exception {
        int databaseSizeBeforeCreate = bottleLifeRepository.findAll().size();

        // Create the BottleLife
        restBottleLifeMockMvc.perform(post("/api/bottleLifes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bottleLife)))
                .andExpect(status().isCreated());

        // Validate the BottleLife in the database
        List<BottleLife> bottleLifes = bottleLifeRepository.findAll();
        assertThat(bottleLifes).hasSize(databaseSizeBeforeCreate + 1);
        BottleLife testBottleLife = bottleLifes.get(bottleLifes.size() - 1);
        assertThat(testBottleLife.getBoughtDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_BOUGHT_DATE);
        assertThat(testBottleLife.getDrinkedDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DRINKED_DATE);
    }

    @Test
    @Transactional
    public void checkDrinkedDateIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bottleLifeRepository.findAll()).hasSize(0);
        // set the field null
        bottleLife.setDrinkedDate(null);

        // Create the BottleLife, which fails.
        restBottleLifeMockMvc.perform(post("/api/bottleLifes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bottleLife)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<BottleLife> bottleLifes = bottleLifeRepository.findAll();
        assertThat(bottleLifes).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllBottleLifes() throws Exception {
        // Initialize the database
        bottleLifeRepository.saveAndFlush(bottleLife);

        // Get all the bottleLifes
        restBottleLifeMockMvc.perform(get("/api/bottleLifes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bottleLife.getId().intValue())))
                .andExpect(jsonPath("$.[*].boughtDate").value(hasItem(DEFAULT_BOUGHT_DATE_STR)))
                .andExpect(jsonPath("$.[*].drinkedDate").value(hasItem(DEFAULT_DRINKED_DATE_STR)));
    }

    @Test
    @Transactional
    public void getBottleLife() throws Exception {
        // Initialize the database
        bottleLifeRepository.saveAndFlush(bottleLife);

        // Get the bottleLife
        restBottleLifeMockMvc.perform(get("/api/bottleLifes/{id}", bottleLife.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bottleLife.getId().intValue()))
            .andExpect(jsonPath("$.boughtDate").value(DEFAULT_BOUGHT_DATE_STR))
            .andExpect(jsonPath("$.drinkedDate").value(DEFAULT_DRINKED_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingBottleLife() throws Exception {
        // Get the bottleLife
        restBottleLifeMockMvc.perform(get("/api/bottleLifes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBottleLife() throws Exception {
        // Initialize the database
        bottleLifeRepository.saveAndFlush(bottleLife);

		int databaseSizeBeforeUpdate = bottleLifeRepository.findAll().size();

        // Update the bottleLife
        bottleLife.setBoughtDate(UPDATED_BOUGHT_DATE);
        bottleLife.setDrinkedDate(UPDATED_DRINKED_DATE);
        restBottleLifeMockMvc.perform(put("/api/bottleLifes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bottleLife)))
                .andExpect(status().isOk());

        // Validate the BottleLife in the database
        List<BottleLife> bottleLifes = bottleLifeRepository.findAll();
        assertThat(bottleLifes).hasSize(databaseSizeBeforeUpdate);
        BottleLife testBottleLife = bottleLifes.get(bottleLifes.size() - 1);
        assertThat(testBottleLife.getBoughtDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_BOUGHT_DATE);
        assertThat(testBottleLife.getDrinkedDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DRINKED_DATE);
    }

    @Test
    @Transactional
    public void deleteBottleLife() throws Exception {
        // Initialize the database
        bottleLifeRepository.saveAndFlush(bottleLife);

		int databaseSizeBeforeDelete = bottleLifeRepository.findAll().size();

        // Get the bottleLife
        restBottleLifeMockMvc.perform(delete("/api/bottleLifes/{id}", bottleLife.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BottleLife> bottleLifes = bottleLifeRepository.findAll();
        assertThat(bottleLifes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
