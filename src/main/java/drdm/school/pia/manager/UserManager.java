package drdm.school.pia.manager;

import drdm.school.pia.domain.entities.User;
import drdm.school.pia.domain.exceptions.UserValidationException;

/**
 * User manager interface - used for managing of users
 * @author Michal Drda
 */
public interface UserManager {

    /**
     * Method for authentication of user's credentials.
     * @param username provided login
     * @param password provided password
     * @return true if username and password are a match, false otherwise
     */
    boolean authenticate(String username, String password);

    /**
     * Method for registering a new user.
     * @param newUser instance with new user data, expected not-null value
     * @param username username can be set manually, otherwise it's generated alphanumeric string
     * @throws UserValidationException if the new user data instance is not in valid state,
     *                                 e.g. required fields are missing
     */
    void register(User newUser, String username) throws UserValidationException;

    /**
     * Method for updating password
     * @param oldPwd provided old password String
     * @param newPwd provided new password String
     * @param username provided username to identify User
     * @throws UserValidationException if the old password is not correct, or new password is the same as the old one
     */
    void updatePassword(String oldPwd, String newPwd, String username) throws UserValidationException;

    /**
     * Method used for updating User object parts
     * @param firstname provided firstname to update
     * @param lastname provided lastname to update
     * @param email provided email to update
     * @param gender provided gender to update
     * @param address provided address to update
     * @param city provided city to update
     * @param zip provided zip to update
     * @param user provided user to update
     * @throws UserValidationException if there is an exception thrown during updating of the user
     */
    void updateUserInfo(String firstname, String lastname, String email, String gender, String address, String city, String zip, User user) throws UserValidationException;

    /**
     * Method used for accessing User entity through UserDao
     * @param username provided username to find User by
     * @return User object found
     */
    User findUserByUsername(String username);

    /**
     * Method finds user by it's account number
     * @param accountNumber account number of the account associated with the user
     * @param bankCode bank code of the account associated with the user
     * @return user instance if user was found or null in case that not
     */
    User findUserByAccount(String accountNumber, String bankCode);

    /**
     * Used for removal of the user
     * @param username provided user name
     */
    void removeUser(String username);

    /**
     * Used for finding of the user by email
     * @param email provided email
     * @return user found or null
     */
    User findUserByEmail(String email);

}
