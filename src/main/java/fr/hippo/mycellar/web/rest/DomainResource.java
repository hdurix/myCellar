package fr.hippo.mycellar.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.hippo.mycellar.domain.Domain;
import fr.hippo.mycellar.repository.AppellationRepository;
import fr.hippo.mycellar.repository.DomainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Domain.
 */
@RestController
@RequestMapping("/api")
public class DomainResource {

    private final Logger log = LoggerFactory.getLogger(DomainResource.class);

    @Inject
    private AppellationRepository appellationRepository;

    @Inject
    private DomainRepository domainRepository;

    /**
     * POST  /domains -> Create a new domain.
     */
    @RequestMapping(value = "/domains",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Domain domain) throws URISyntaxException {
        log.debug("REST request to save Domain : {}", domain);
        if (domain.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new domain cannot already have an ID").build();
        }
        domainRepository.save(domain);
        return ResponseEntity.created(new URI("/api/domains/" + domain.getId())).build();
    }

    /**
     * PUT  /domains -> Updates an existing domain.
     */
    @RequestMapping(value = "/domains",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Domain domain) throws URISyntaxException {
        log.debug("REST request to update Domain : {}", domain);
        if (domain.getId() == null) {
            return create(domain);
        }
        domainRepository.save(domain);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /domains -> get all the domains.
     */
    @RequestMapping(value = "/domains",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Domain> getAll(@RequestParam(value = "appellationId", required = false) Long appellationId) {
        log.debug("REST request to get all Domains");
        if (appellationId != null) {
            return domainRepository.findAllByAppellation(appellationRepository.findOne(appellationId));
        }
        return domainRepository.findAllWithAppellation();
    }

    /**
     * GET  /domains/:id -> get the "id" domain.
     */
    @RequestMapping(value = "/domains/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Domain> get(@PathVariable Long id) {
        log.debug("REST request to get Domain : {}", id);
        return Optional.ofNullable(domainRepository.findOne(id))
            .map(domain -> new ResponseEntity<>(
                domain,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /domains/:id -> delete the "id" domain.
     */
    @RequestMapping(value = "/domains/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Domain : {}", id);
        domainRepository.delete(id);
    }
}
