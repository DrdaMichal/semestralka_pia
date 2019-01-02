package drdm.school.pia.dao.implementation;

import drdm.school.pia.dao.UserDao;
import drdm.school.pia.domain.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * JPA implementation of the UserDao interface
 * @author Michal Drda
 */
@Repository
public class UserDaoJpa extends GenericDaoJpa<User, String> implements UserDao {

    /**
     * Constructor
     */
    public UserDaoJpa() {
        // natvrdo davam User jako typ objektu, abych vedel v GenericDaoJpa, co zrovna pouzivam za objekt
        super(User.class);
    }

    /**
     * @inheritDoc
     * This is an implementation of findByUsername method in UserDao interface
     */
    @Override
    public User findByUsername(String username) {
        TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        q.setParameter("username", username);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * @inheritDoc
     * This is an implementation of findByAccountNo method in UserDao interface
     */
    @Override
    public User findByAccountNo(String account, String bankCode) {
        TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u LEFT JOIN Account a ON a.id = u.account.id WHERE a.number = :account and a.bank = :bankCode", User.class);
        q.setParameter("account", account);
        q.setParameter("bankCode", bankCode);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
