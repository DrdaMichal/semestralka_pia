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

    User findByAccountNo(String accountNo);

    User findByCardNo(String card);

    User checkUniqueness(String accountNo, String username, String card);

    User create(User user);

}
