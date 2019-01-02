package drdm.school.pia.dao.implementation;

import drdm.school.pia.dao.PaymentDao;
import drdm.school.pia.domain.entities.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * JPA implementation of data access object for Payment entity
 * @author Michal Drda
 */
@Repository
public class PaymentDaoJpa extends GenericDaoJpa<Payment, Long> implements PaymentDao {

    /**
     * Constructor
     */
    public PaymentDaoJpa() {
        super(Payment.class);
    }

    /**
     * @inheritDoc
     * This is an implementation of findTemplatesByUsername method in PaymentDao interface
     */
    @Override
    public List<Payment> findTemplatesByUsername(String username) {
        TypedQuery<Payment> q = entityManager.createQuery("SELECT p FROM User u LEFT JOIN Account a ON u.account.id = a.id LEFT JOIN Payment p ON a.number = p.senderAccount and a.bank = p.senderBankCode WHERE u.username = :username and not(p.template = '') and p.senderPreAccountNumber = ''", Payment.class);
        q.setParameter("username", username);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * @inheritDoc
     * This is an implementation of findPaymentByTemplate method in PaymentDao interface
     */
    @Override
    public Payment findPaymentByTemplate(String username, String template) {
        TypedQuery<Payment> q = entityManager.createQuery("SELECT p FROM User u LEFT JOIN Account a ON u.account.id = a.id LEFT JOIN Payment p ON a.number = p.senderAccount and a.bank = p.senderBankCode WHERE u.username = :username and p.template = :template and p.senderPreAccountNumber = ''", Payment.class);
        q.setParameter("template", template);
        q.setParameter("username", username);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * @inheritDoc
     * This is an implementation of findTransactionsByAccount method in PaymentDao interface
     */
    @Override
    public List<Payment> findTransactionsByAccount(String account, String bankCode) {
        TypedQuery<Payment> q = entityManager.createQuery("SELECT p FROM Account a LEFT JOIN Payment p ON  a.number = p.senderAccount and a.bank = p.senderBankCode WHERE (p.recipientAccount = :account and p.recipientBankCode = :bank) or (a.number = :account and a.bank = :bank) ORDER BY p.transactionDate DESC, p.created DESC", Payment.class);
        q.setParameter("account", account);
        q.setParameter("bank", bankCode);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
