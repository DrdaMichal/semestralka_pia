package drdm.school.pia.manager;

import drdm.school.pia.domain.Role;
import drdm.school.pia.domain.User;
import drdm.school.pia.domain.UserValidationException;

/**
 * @author Michal Drda
 */
public interface RoleManager {

    /**
     * Adds a role to user (currently user should have just for adding one role, not multiple)
     * @param user
     * @param roleName
     * @throws UserValidationException
     */
    void addRoleToUser(User user, String roleName) throws UserValidationException;

    /**
     * Returns a Role assigned to user (expecting user to have just one role)
     * @param username
     * @return
     */
    Role getUserRoleByUsername(String username);

}
