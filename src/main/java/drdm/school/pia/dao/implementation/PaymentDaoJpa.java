package drdm.school.pia.dao.implementation;

import drdm.school.pia.dao.PaymentDao;
import drdm.school.pia.domain.entities.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PaymentDaoJpa extends GenericDaoJpa<Payment, Long> implements PaymentDao {

    public PaymentDaoJpa() {
        super(Payment.class);
    }

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
