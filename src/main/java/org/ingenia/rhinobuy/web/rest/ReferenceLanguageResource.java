package org.ingenia.rhinobuy.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.ingenia.rhinobuy.domain.ReferenceLanguage;
import org.ingenia.rhinobuy.service.ReferenceLanguageService;
import org.ingenia.rhinobuy.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ReferenceLanguage.
 */
@RestController
@RequestMapping("/api")
public class ReferenceLanguageResource {

    private final Logger log = LoggerFactory.getLogger(ReferenceLanguageResource.class);
        
    @Inject
    private ReferenceLanguageService referenceLanguageService;

    /**
     * POST  /reference-languages : Create a new referenceLanguage.
     *
     * @param referenceLanguage the referenceLanguage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new referenceLanguage, or with status 400 (Bad Request) if the referenceLanguage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reference-languages")
    @Timed
    public ResponseEntity<ReferenceLanguage> createReferenceLanguage(@RequestBody ReferenceLanguage referenceLanguage) throws URISyntaxException {
        log.debug("REST request to save ReferenceLanguage : {}", referenceLanguage);
        if (referenceLanguage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("referenceLanguage", "idexists", "A new referenceLanguage cannot already have an ID")).body(null);
        }
        ReferenceLanguage result = referenceLanguageService.save(referenceLanguage);
        return ResponseEntity.created(new URI("/api/reference-languages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("referenceLanguage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reference-languages : Updates an existing referenceLanguage.
     *
     * @param referenceLanguage the referenceLanguage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated referenceLanguage,
     * or with status 400 (Bad Request) if the referenceLanguage is not valid,
     * or with status 500 (Internal Server Error) if the referenceLanguage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reference-languages")
    @Timed
    public ResponseEntity<ReferenceLanguage> updateReferenceLanguage(@RequestBody ReferenceLanguage referenceLanguage) throws URISyntaxException {
        log.debug("REST request to update ReferenceLanguage : {}", referenceLanguage);
        if (referenceLanguage.getId() == null) {
            return createReferenceLanguage(referenceLanguage);
        }
        ReferenceLanguage result = referenceLanguageService.save(referenceLanguage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("referenceLanguage", referenceLanguage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reference-languages : get all the referenceLanguages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of referenceLanguages in body
     */
    @GetMapping("/reference-languages")
    @Timed
    public List<ReferenceLanguage> getAllReferenceLanguages() {
        log.debug("REST request to get all ReferenceLanguages");
        return referenceLanguageService.findAll();
    }

    /**
     * GET  /reference-languages/:id : get the "id" referenceLanguage.
     *
     * @param id the id of the referenceLanguage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the referenceLanguage, or with status 404 (Not Found)
     */
    @GetMapping("/reference-languages/{id}")
    @Timed
    public ResponseEntity<ReferenceLanguage> getReferenceLanguage(@PathVariable Long id) {
        log.debug("REST request to get ReferenceLanguage : {}", id);
        ReferenceLanguage referenceLanguage = referenceLanguageService.findOne(id);
        return Optional.ofNullable(referenceLanguage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /reference-languages/:id : delete the "id" referenceLanguage.
     *
     * @param id the id of the referenceLanguage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reference-languages/{id}")
    @Timed
    public ResponseEntity<Void> deleteReferenceLanguage(@PathVariable Long id) {
        log.debug("REST request to delete ReferenceLanguage : {}", id);
        referenceLanguageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("referenceLanguage", id.toString())).build();
    }

    /**
     * SEARCH  /_search/reference-languages?query=:query : search for the referenceLanguage corresponding
     * to the query.
     *
     * @param query the query of the referenceLanguage search 
     * @return the result of the search
     */
    @GetMapping("/_search/reference-languages")
    @Timed
    public List<ReferenceLanguage> searchReferenceLanguages(@RequestParam String query) {
        log.debug("REST request to search ReferenceLanguages for query {}", query);
        return referenceLanguageService.search(query);
    }


}
