package drdm.school.pia.dao;

import drdm.school.pia.domain.Role;

import java.util.Set;

/**
 * DAO interface for the Role entity
 * @author Michal Drda
 */
public interface RoleDao extends GenericDao<Role, String> {

    /**
     *
     * @param username username of the user in scope
     * @return role associated with the given user
     */
    Role findByUser(String username);

}
