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
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Payment findByAccountNumber(String account) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public Payment create(Payment payment) {
        entityManager.persist(payment);
        return payment;
    }
}
