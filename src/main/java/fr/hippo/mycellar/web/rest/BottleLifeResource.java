package fr.hippo.mycellar.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.hippo.mycellar.domain.BottleLife;
import fr.hippo.mycellar.repository.BottleLifeRepository;
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
 * REST controller for managing BottleLife.
 */
@RestController
@RequestMapping("/api")
public class BottleLifeResource {

    private final Logger log = LoggerFactory.getLogger(BottleLifeResource.class);

    @Inject
    private BottleLifeRepository bottleLifeRepository;

    /**
     * POST  /bottleLifes -> Create a new bottleLife.
     */
    @RequestMapping(value = "/bottleLifes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody BottleLife bottleLife) throws URISyntaxException {
        log.debug("REST request to save BottleLife : {}", bottleLife);
        if (bottleLife.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bottleLife cannot already have an ID").build();
        }
        bottleLifeRepository.save(bottleLife);
        return ResponseEntity.created(new URI("/api/bottleLifes/" + bottleLife.getId())).build();
    }

    /**
     * PUT  /bottleLifes -> Updates an existing bottleLife.
     */
    @RequestMapping(value = "/bottleLifes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody BottleLife bottleLife) throws URISyntaxException {
        log.debug("REST request to update BottleLife : {}", bottleLife);
        if (bottleLife.getId() == null) {
            return create(bottleLife);
        }
        bottleLifeRepository.save(bottleLife);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /bottleLifes -> get all the bottleLifes.
     */
    @RequestMapping(value = "/bottleLifes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BottleLife>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<BottleLife> page = bottleLifeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bottleLifes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bottleLifes/:id -> get the "id" bottleLife.
     */
    @RequestMapping(value = "/bottleLifes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BottleLife> get(@PathVariable Long id) {
        log.debug("REST request to get BottleLife : {}", id);
        return Optional.ofNullable(bottleLifeRepository.findOne(id))
            .map(bottleLife -> new ResponseEntity<>(
                bottleLife,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bottleLifes/:id -> delete the "id" bottleLife.
     */
    @RequestMapping(value = "/bottleLifes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete BottleLife : {}", id);
        bottleLifeRepository.delete(id);
    }
}
