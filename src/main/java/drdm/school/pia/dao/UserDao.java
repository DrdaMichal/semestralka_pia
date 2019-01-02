package drdm.school.pia.dao;

import drdm.school.pia.domain.entities.User;

/**
 * DAO interface for the User entity
 * @author Michal Drda
 */
public interface UserDao extends GenericDao<User, String> {

    /**
     * Finds a user by it's user name
     * @param username the requested user name
     * @return user with the given user name or null
     */
    User findByUsername(String username);

    /**
     * Finds a user by it's account number
     * @param accountNo account number provided
     * @return user associated with the account found by account number or null
     */
    User findByAccountNo(String accountNo, String bankCode);

}
