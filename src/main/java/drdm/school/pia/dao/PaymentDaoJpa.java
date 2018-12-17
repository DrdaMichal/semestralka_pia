package drdm.school.pia.dao;

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
        TypedQuery<Payment> q = entityManager.createQuery("SELECT u FROM Payment u WHERE u.userAccount = :username", Payment.class);
        q.setParameter("username", username);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Payment findByAccountNumber(String accountNumber) {
        //TODO
        return null;
    }

    public Payment create(Payment payment) {
        entityManager.persist(payment);
        return payment;
    }
}
