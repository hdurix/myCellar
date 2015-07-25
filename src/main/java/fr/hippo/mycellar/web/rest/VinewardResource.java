package fr.hippo.mycellar.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.hippo.mycellar.domain.Vineward;
import fr.hippo.mycellar.repository.DomainRepository;
import fr.hippo.mycellar.repository.VinewardRepository;
import fr.hippo.mycellar.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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
 * REST controller for managing Vineward.
 */
@RestController
@RequestMapping("/api")
public class VinewardResource {

    private final Logger log = LoggerFactory.getLogger(VinewardResource.class);

    @Inject
    private VinewardRepository vinewardRepository;

    @Inject
    private DomainRepository domainRepository;

    /**
     * POST  /vinewards -> Create a new vineward.
     */
    @RequestMapping(value = "/vinewards",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Vineward> create(@Valid @RequestBody Vineward vineward) throws URISyntaxException {
        log.debug("REST request to save Vineward : {}", vineward);
        if (vineward.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new vineward cannot already have an ID").body(null);
        }
        Vineward savedVineward = vinewardRepository.save(vineward);
        return new ResponseEntity<>(savedVineward, HttpStatus.OK);
    }

    /**
     * PUT  /vinewards -> Updates an existing vineward.
     */
    @RequestMapping(value = "/vinewards",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Vineward> update(@Valid @RequestBody Vineward vineward) throws URISyntaxException {
        log.debug("REST request to update Vineward : {}", vineward);
        if (vineward.getId() == null) {
            return create(vineward);
        }
        Vineward savedVineward = vinewardRepository.save(vineward);
        return new ResponseEntity<>(savedVineward, HttpStatus.OK);
    }

    /**
     * GET  /vinewards -> get all the vinewards.
     */
    @RequestMapping(value = "/vinewards",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Vineward>> getAll(@RequestParam(value = "page", required = false) Integer offset,
                                                 @RequestParam(value = "per_page", required = false) Integer limit,
                                                 @RequestParam(value = "domainId", required = false) Long domainId)
        throws URISyntaxException {
        log.debug("REST request to get all Domains");
        if (domainId != null) {
            return new ResponseEntity<>(vinewardRepository.findAllByDomain(domainRepository.findOne(domainId)), HttpStatus.OK);
        }
        Page<Vineward> page = vinewardRepository.findAllWithDomain(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vinewards", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vinewards/:id -> get the "id" vineward.
     */
    @RequestMapping(value = "/vinewards/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Vineward> get(@PathVariable Long id) {
        log.debug("REST request to get Vineward : {}", id);
        return Optional.ofNullable(vinewardRepository.findOne(id))
            .map(vineward -> new ResponseEntity<>(
                vineward,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vinewards/:id -> delete the "id" vineward.
     */
    @RequestMapping(value = "/vinewards/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Vineward : {}", id);
        vinewardRepository.delete(id);
    }
}
