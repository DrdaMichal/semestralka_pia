package drdm.school.pia.dao;

import drdm.school.pia.domain.entities.Payment;

import java.util.List;

/**
 * DAO interface for Payment entity
 * @author Michal Drda
 */
public interface PaymentDao extends GenericDao<Payment, Long> {

    List<Payment> findTemplatesByUsername(String username);

    Payment findPaymentByTemplate(String username, String template);

    List<Payment> findTransactionsByAccount(String account, String bankCode);

}
