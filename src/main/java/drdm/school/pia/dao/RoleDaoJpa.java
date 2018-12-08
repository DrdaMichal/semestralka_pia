package drdm.school.pia.dao;

import drdm.school.pia.domain.Role;

import javax.persistence.EntityManager;

/**
 * JPA implementation of the RoleDao interface
 * @author Michal Drda
 */
public class RoleDaoJpa extends GenericDaoJpa<Role, String> implements RoleDao {

    /**
     *
     * @param em entity manager to be used by this instance
     */
    public RoleDaoJpa(EntityManager em) {
        super(em, Role.class);
    }

    @Override
    public Role findByUser(String username) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
