package drdm.school.pia.dao.implementation;

import drdm.school.pia.dao.PaymentDao;
import drdm.school.pia.domain.Payment;
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
    public List<Payment> findByUsername(String username) {
        TypedQuery<Payment> q = entityManager.createQuery("SELECT p FROM User u LEFT JOIN Account a ON u.account.id = a.id LEFT JOIN Payment p ON a.id = p.account.id WHERE u.username = :username", Payment.class);
        q.setParameter("username", username);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Payment> findByAccountNumber(String account) {
        TypedQuery<Payment> q = entityManager.createQuery("SELECT p FROM User u LEFT JOIN Account a ON u.account.id = a.id LEFT JOIN Payment p ON a.id = p.account.id WHERE p.account.number = :account", Payment.class);
        q.setParameter("account", account);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Payment> findTemplatesByAccountNumber(String account) {
        TypedQuery<Payment> q = entityManager.createQuery("SELECT p FROM User u LEFT JOIN Account a ON u.account.id = a.id LEFT JOIN Payment p ON a.id = p.account.id WHERE p.account.number = :account and not(p.template = '')", Payment.class);
        q.setParameter("account", account);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Payment> findTemplatesByUsername(String username) {
        TypedQuery<Payment> q = entityManager.createQuery("SELECT p FROM User u LEFT JOIN Account a ON u.account.id = a.id LEFT JOIN Payment p ON a.id = p.account.id WHERE u.username = :username and not(p.template = '')", Payment.class);
        q.setParameter("username", username);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Payment findPaymentByTemplate(String username, String template) {
        TypedQuery<Payment> q = entityManager.createQuery("SELECT p FROM User u LEFT JOIN Account a ON u.account.id = a.id LEFT JOIN Payment p ON a.id = p.account.id WHERE u.username = :username and p.template = :template", Payment.class);
        q.setParameter("template", template);
        q.setParameter("username", username);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Payment create(Payment payment) {
        entityManager.persist(payment);
        return payment;
    }
}
