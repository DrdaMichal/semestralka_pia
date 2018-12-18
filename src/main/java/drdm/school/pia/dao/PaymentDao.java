package drdm.school.pia.dao;

import drdm.school.pia.domain.Payment;

/**
 * DAO interface for Payment entity
 * @author Michal Drda
 */
public interface PaymentDao extends GenericDao<Payment, Long> {

    /**
     *
     * @param username
     * @return
     */
    Payment findByUsername(String username);

}
