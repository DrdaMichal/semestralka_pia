package drdm.school.pia.manager;

import drdm.school.pia.domain.entities.Role;
import drdm.school.pia.domain.entities.User;
import drdm.school.pia.domain.exceptions.UserValidationException;

/**
 * Role manager - user for managing of the roles for users
 * @author Michal Drda
 */
public interface RoleManager {

    /**
     * Adds a role to user (currently user should have just for adding one role, not multiple)
     * @param user provided user
     * @param roleName provided role name
     * @throws UserValidationException in case of invalid user parameters
     */
    void addRoleToUser(User user, String roleName) throws UserValidationException;

    /**
     * Returns a Role assigned to user (expecting user to have just one role)
     * @param username provided user name
     * @return user Role object
     */
    Role getUserRoleByUsername(String username);

}
