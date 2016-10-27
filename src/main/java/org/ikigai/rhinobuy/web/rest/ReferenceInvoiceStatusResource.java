package org.ikigai.rhinobuy.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.ikigai.rhinobuy.domain.ReferenceInvoiceStatus;
import org.ikigai.rhinobuy.service.ReferenceInvoiceStatusService;
import org.ikigai.rhinobuy.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ReferenceInvoiceStatus.
 */
@RestController
@RequestMapping("/api")
public class ReferenceInvoiceStatusResource {

    private final Logger log = LoggerFactory.getLogger(ReferenceInvoiceStatusResource.class);
        
    @Inject
    private ReferenceInvoiceStatusService referenceInvoiceStatusService;

    /**
     * POST  /reference-invoice-statuses : Create a new referenceInvoiceStatus.
     *
     * @param referenceInvoiceStatus the referenceInvoiceStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new referenceInvoiceStatus, or with status 400 (Bad Request) if the referenceInvoiceStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/reference-invoice-statuses",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReferenceInvoiceStatus> createReferenceInvoiceStatus(@RequestBody ReferenceInvoiceStatus referenceInvoiceStatus) throws URISyntaxException {
        log.debug("REST request to save ReferenceInvoiceStatus : {}", referenceInvoiceStatus);
        if (referenceInvoiceStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("referenceInvoiceStatus", "idexists", "A new referenceInvoiceStatus cannot already have an ID")).body(null);
        }
        ReferenceInvoiceStatus result = referenceInvoiceStatusService.save(referenceInvoiceStatus);
        return ResponseEntity.created(new URI("/api/reference-invoice-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("referenceInvoiceStatus", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reference-invoice-statuses : Updates an existing referenceInvoiceStatus.
     *
     * @param referenceInvoiceStatus the referenceInvoiceStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated referenceInvoiceStatus,
     * or with status 400 (Bad Request) if the referenceInvoiceStatus is not valid,
     * or with status 500 (Internal Server Error) if the referenceInvoiceStatus couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/reference-invoice-statuses",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReferenceInvoiceStatus> updateReferenceInvoiceStatus(@RequestBody ReferenceInvoiceStatus referenceInvoiceStatus) throws URISyntaxException {
        log.debug("REST request to update ReferenceInvoiceStatus : {}", referenceInvoiceStatus);
        if (referenceInvoiceStatus.getId() == null) {
            return createReferenceInvoiceStatus(referenceInvoiceStatus);
        }
        ReferenceInvoiceStatus result = referenceInvoiceStatusService.save(referenceInvoiceStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("referenceInvoiceStatus", referenceInvoiceStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reference-invoice-statuses : get all the referenceInvoiceStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of referenceInvoiceStatuses in body
     */
    @RequestMapping(value = "/reference-invoice-statuses",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ReferenceInvoiceStatus> getAllReferenceInvoiceStatuses() {
        log.debug("REST request to get all ReferenceInvoiceStatuses");
        return referenceInvoiceStatusService.findAll();
    }

    /**
     * GET  /reference-invoice-statuses/:id : get the "id" referenceInvoiceStatus.
     *
     * @param id the id of the referenceInvoiceStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the referenceInvoiceStatus, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/reference-invoice-statuses/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReferenceInvoiceStatus> getReferenceInvoiceStatus(@PathVariable Long id) {
        log.debug("REST request to get ReferenceInvoiceStatus : {}", id);
        ReferenceInvoiceStatus referenceInvoiceStatus = referenceInvoiceStatusService.findOne(id);
        return Optional.ofNullable(referenceInvoiceStatus)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /reference-invoice-statuses/:id : delete the "id" referenceInvoiceStatus.
     *
     * @param id the id of the referenceInvoiceStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/reference-invoice-statuses/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteReferenceInvoiceStatus(@PathVariable Long id) {
        log.debug("REST request to delete ReferenceInvoiceStatus : {}", id);
        referenceInvoiceStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("referenceInvoiceStatus", id.toString())).build();
    }

}
