package drdm.school.pia.dao.implementation;

import drdm.school.pia.dao.PaymentDao;
import drdm.school.pia.domain.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class PaymentDaoJpa extends GenericDaoJpa<Payment, Long> implements PaymentDao {

    public PaymentDaoJpa() {
        super(Payment.class);
    }

    @Override
    public Payment findByUsername(String username) {
        TypedQuery<Payment> q = entityManager.createQuery("SELECT p FROM Payment p WHERE p.user = :user_account", Payment.class);
        q.setParameter("user_account", username);
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
