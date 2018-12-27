package drdm.school.pia.dao;

import drdm.school.pia.domain.entities.User;

/**
 * @author Michal Drda
 */
public interface UserDao extends GenericDao<User, String> {

    /**
     *
     * @param username the requested username
     * @return user with the given username or null
     */
    User findByUsername(String username);

    User findByAccountNo(String accountNo);

    User checkUniqueness(String accountNo, String username);

    User create(User user);

}
