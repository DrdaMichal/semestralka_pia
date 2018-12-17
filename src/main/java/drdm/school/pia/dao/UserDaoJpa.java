package drdm.school.pia.dao;

import drdm.school.pia.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * JPA implementation of the UserDao interface
 *
 * @author Michal Drda
 */
@Repository
public class UserDaoJpa extends GenericDaoJpa<User, String> implements UserDao {

    /**
     *
     */
    public UserDaoJpa() {
        // natvrdo davam User jako typ objektu, abych vedel v GenericDaoJpa, co zrovna pouzivam za objekt
        super(User.class);
    }

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

    public User findByAccountNo(String account) {
        TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u WHERE u.account = :account", User.class);
        q.setParameter("account", account);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findByCardNo(String card) {
        TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u WHERE u.card = :card", User.class);
        q.setParameter("card", card);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User checkUniqueness(String username, String account, String card) {
        TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u WHERE u.account = :account or u.username = :username or u.card = :card", User.class);
        q.setParameter("account", account);
        q.setParameter("username", username);
        q.setParameter("card", card);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }
}
