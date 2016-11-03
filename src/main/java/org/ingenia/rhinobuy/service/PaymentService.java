package org.ingenia.rhinobuy.service;

import org.ingenia.rhinobuy.domain.Payment;

import java.util.List;

/**
 * Service Interface for managing Payment.
 */
public interface PaymentService {

    /**
     * Save a payment.
     *
     * @param payment the entity to save
     * @return the persisted entity
     */
    Payment save(Payment payment);

    /**
     *  Get all the payments.
     *  
     *  @return the list of entities
     */
    List<Payment> findAll();

    /**
     *  Get the "id" payment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Payment findOne(Long id);

    /**
     *  Delete the "id" payment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the payment corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Payment> search(String query);
}
