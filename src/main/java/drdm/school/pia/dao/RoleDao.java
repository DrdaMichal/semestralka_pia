package drdm.school.pia.dao;

import drdm.school.pia.domain.Role;

/**
 * DAO interface for the Role entity
 * @author Michal Drda
 */
public interface RoleDao extends GenericDao<Role, Long> {

    /**
     *
     * @param username username of the user in scope
     * @return role associated with the given user
     */
    Role findByUser(String username);

    Role findByRoleName(String roleName);
}
