package drdm.school.pia.dao;

import drdm.school.pia.domain.User;

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

    User create(User user);

}
