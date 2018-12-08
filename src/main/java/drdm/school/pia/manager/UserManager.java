package drdm.school.pia.manager;

import drdm.school.pia.domain.Role;
import drdm.school.pia.domain.User;
import drdm.school.pia.domain.UserValidationException;

/**
 * @author Michal Drda
 */
public interface UserManager {

    /**
     * Method for authentication of user's credentials.
     *
     * @param username provided login
     * @param password provided password
     * @return true if username and password are a match, false otherwise
     */
    boolean authenticate(String username, String password);

    /**
     * Method for registering a new user.
     * @param newUser instance with new user data, expected not-null value
     * @throws UserValidationException if the new user data instance is not in valid state,
     *                                 e.g. required fields are missing
     */
    void register(User newUser) throws UserValidationException;


    Role userRole(String username);


}
