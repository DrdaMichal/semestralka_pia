package drdm.school.pia.dao;

import drdm.school.pia.domain.entities.Payment;

import java.util.List;

/**
 * DAO interface for Payment entity
 * @author Michal Drda
 */
public interface PaymentDao extends GenericDao<Payment, Long> {

    /**
     * Finds all templates by given username
     * @param username provided user name
     * @return list of payments that are templates or null
     */
    List<Payment> findTemplatesByUsername(String username);

    /**
     * Finds a payment by a template name for a user
     * @param username provided user name
     * @param template provided template name
     * @return payment instance or null
     */
    Payment findPaymentByTemplate(String username, String template);

    /**
     * Finds all payments connected with a user account and bankCode
     * @param account provided account number
     * @param bankCode provided bank code
     * @return list of payments associated with provided account number and bankcode or null
     */
    List<Payment> findTransactionsByAccount(String account, String bankCode);

}
