package fr.hippo.mycellar.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.hippo.mycellar.domain.Bottle;
import fr.hippo.mycellar.repository.BottleRepository;
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
 * REST controller for managing Bottle.
 */
@RestController
@RequestMapping("/api")
public class BottleResource {

    private final Logger log = LoggerFactory.getLogger(BottleResource.class);

    @Inject
    private BottleRepository bottleRepository;

    /**
     * POST  /bottles -> Create a new bottle.
     */
    @RequestMapping(value = "/bottles",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Bottle bottle) throws URISyntaxException {
        log.debug("REST request to save Bottle : {}", bottle);
        if (bottle.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bottle cannot already have an ID").build();
        }
        bottleRepository.save(bottle);
        return ResponseEntity.created(new URI("/api/bottles/" + bottle.getId())).build();
    }

    /**
     * PUT  /bottles -> Updates an existing bottle.
     */
    @RequestMapping(value = "/bottles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Bottle bottle) throws URISyntaxException {
        log.debug("REST request to update Bottle : {}", bottle);
        if (bottle.getId() == null) {
            return create(bottle);
        }
        bottleRepository.save(bottle);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /bottles -> get all the bottles.
     */
    @RequestMapping(value = "/bottles",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Bottle>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Bottle> page = bottleRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bottles", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bottles/:id -> get the "id" bottle.
     */
    @RequestMapping(value = "/bottles/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bottle> get(@PathVariable Long id) {
        log.debug("REST request to get Bottle : {}", id);
        return Optional.ofNullable(bottleRepository.findOne(id))
            .map(bottle -> new ResponseEntity<>(
                bottle,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bottles/:id -> delete the "id" bottle.
     */
    @RequestMapping(value = "/bottles/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Bottle : {}", id);
        bottleRepository.delete(id);
    }
}
