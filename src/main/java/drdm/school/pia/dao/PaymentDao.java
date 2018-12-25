package drdm.school.pia.dao;

import drdm.school.pia.domain.Payment;

import java.util.List;

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
    List<Payment> findByUsername(String username);

    List<Payment> findByAccountNumber(String account);

    List<Payment> findTemplatesByAccountNumber(String account);

    List<Payment> findTemplatesByUsername(String username);

    Payment findPaymentByTemplate(String username, String template);

}
