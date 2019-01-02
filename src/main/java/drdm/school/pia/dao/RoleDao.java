package drdm.school.pia.dao;

import drdm.school.pia.domain.entities.Role;

/**
 * DAO interface for the Role entity
 * @author Michal Drda
 */
public interface RoleDao extends GenericDao<Role, Long> {

    /**
     * Find role associated with a given user
     * @param username username of the user in scope
     * @return role instance associated with a given user or null
     */
    Role findByUserName(String username);

    /**
     * Find role by it's name
     * @param roleName name of the role in scope
     * @return role instance with given name or null
     */
    Role findByRoleName(String roleName);

}
