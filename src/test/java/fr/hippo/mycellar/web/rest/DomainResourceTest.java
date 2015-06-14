package fr.hippo.mycellar.web.rest;

import fr.hippo.mycellar.Application;
import fr.hippo.mycellar.domain.Domain;
import fr.hippo.mycellar.repository.DomainRepository;

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
 * Test class for the DomainResource REST controller.
 *
 * @see DomainResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DomainResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    @Inject
    private DomainRepository domainRepository;

    private MockMvc restDomainMockMvc;

    private Domain domain;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DomainResource domainResource = new DomainResource();
        ReflectionTestUtils.setField(domainResource, "domainRepository", domainRepository);
        this.restDomainMockMvc = MockMvcBuilders.standaloneSetup(domainResource).build();
    }

    @Before
    public void initTest() {
        domain = new Domain();
        domain.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDomain() throws Exception {
        int databaseSizeBeforeCreate = domainRepository.findAll().size();

        // Create the Domain
        restDomainMockMvc.perform(post("/api/domains")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domain)))
                .andExpect(status().isCreated());

        // Validate the Domain in the database
        List<Domain> domains = domainRepository.findAll();
        assertThat(domains).hasSize(databaseSizeBeforeCreate + 1);
        Domain testDomain = domains.get(domains.size() - 1);
        assertThat(testDomain.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(domainRepository.findAll()).hasSize(0);
        // set the field null
        domain.setName(null);

        // Create the Domain, which fails.
        restDomainMockMvc.perform(post("/api/domains")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domain)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Domain> domains = domainRepository.findAll();
        assertThat(domains).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllDomains() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);

        // Get all the domains
        restDomainMockMvc.perform(get("/api/domains"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(domain.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDomain() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);

        // Get the domain
        restDomainMockMvc.perform(get("/api/domains/{id}", domain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(domain.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDomain() throws Exception {
        // Get the domain
        restDomainMockMvc.perform(get("/api/domains/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomain() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);

		int databaseSizeBeforeUpdate = domainRepository.findAll().size();

        // Update the domain
        domain.setName(UPDATED_NAME);
        restDomainMockMvc.perform(put("/api/domains")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domain)))
                .andExpect(status().isOk());

        // Validate the Domain in the database
        List<Domain> domains = domainRepository.findAll();
        assertThat(domains).hasSize(databaseSizeBeforeUpdate);
        Domain testDomain = domains.get(domains.size() - 1);
        assertThat(testDomain.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteDomain() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);

		int databaseSizeBeforeDelete = domainRepository.findAll().size();

        // Get the domain
        restDomainMockMvc.perform(delete("/api/domains/{id}", domain.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Domain> domains = domainRepository.findAll();
        assertThat(domains).hasSize(databaseSizeBeforeDelete - 1);
    }
}
