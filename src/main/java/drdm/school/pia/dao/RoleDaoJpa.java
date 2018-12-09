package drdm.school.pia.dao;

import drdm.school.pia.domain.Role;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * JPA implementation of the RoleDao interface
 * @author Michal Drda
 */
@Repository
public class RoleDaoJpa extends GenericDaoJpa<Role, String> implements RoleDao {

    /**
     *
     *
     */
    public RoleDaoJpa() {
        super(Role.class);
    }

    @Override
    public Role findByUser(String username) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
