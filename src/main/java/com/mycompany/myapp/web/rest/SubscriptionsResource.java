package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Subscriptions;
import com.mycompany.myapp.repository.SubscriptionsRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
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
 * REST controller for managing Subscriptions.
 */
@RestController
@RequestMapping("/api")
public class SubscriptionsResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionsResource.class);

    @Inject
    private SubscriptionsRepository subscriptionsRepository;

    /**
     * POST  /subscriptionss -> Create a new subscriptions.
     */
    @RequestMapping(value = "/subscriptionss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Subscriptions> createSubscriptions(@RequestBody Subscriptions subscriptions) throws URISyntaxException {
        log.debug("REST request to save Subscriptions : {}", subscriptions);
        if (subscriptions.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new subscriptions cannot already have an ID").body(null);
        }
        Subscriptions result = subscriptionsRepository.save(subscriptions);
        return ResponseEntity.created(new URI("/api/subscriptionss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("subscriptions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subscriptionss -> Updates an existing subscriptions.
     */
    @RequestMapping(value = "/subscriptionss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Subscriptions> updateSubscriptions(@RequestBody Subscriptions subscriptions) throws URISyntaxException {
        log.debug("REST request to update Subscriptions : {}", subscriptions);
        if (subscriptions.getId() == null) {
            return createSubscriptions(subscriptions);
        }
        Subscriptions result = subscriptionsRepository.save(subscriptions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("subscriptions", subscriptions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subscriptionss -> get all the subscriptionss.
     */
    @RequestMapping(value = "/subscriptionss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Subscriptions> getAllSubscriptionss() {
        log.debug("REST request to get all Subscriptionss");
        return subscriptionsRepository.findAll();
    }

    /**
     * GET  /subscriptionss/:id -> get the "id" subscriptions.
     */
    @RequestMapping(value = "/subscriptionss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Subscriptions> getSubscriptions(@PathVariable Long id) {
        log.debug("REST request to get Subscriptions : {}", id);
        return Optional.ofNullable(subscriptionsRepository.findOne(id))
            .map(subscriptions -> new ResponseEntity<>(
                subscriptions,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /subscriptionss/:id -> delete the "id" subscriptions.
     */
    @RequestMapping(value = "/subscriptionss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSubscriptions(@PathVariable Long id) {
        log.debug("REST request to delete Subscriptions : {}", id);
        subscriptionsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("subscriptions", id.toString())).build();
    }
}
