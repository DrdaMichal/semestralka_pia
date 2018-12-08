package drdm.school.pia.dao;

import drdm.school.pia.domain.User;

import javax.persistence.EntityManager;

/**
 * JPA implementation of the UserDao interface
 * @author Michal Drda
 */
public class UserDaoJpa extends GenericDaoJpa<User, String> implements UserDao {

    /**
     *
     * @param em entity manager to be used by this dao
     */
    public UserDaoJpa(EntityManager em) {
        // natvrdo davam User jako typ objektu, abych vedel v GenericDaoJpa, co zrovna pouzivam za objekt
        super(em, User.class);
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }
}
