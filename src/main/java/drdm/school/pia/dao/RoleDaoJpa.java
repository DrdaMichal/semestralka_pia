package drdm.school.pia.dao;

import drdm.school.pia.domain.Role;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * JPA implementation of the RoleDao interface
 * @author Michal Drda
 */
@Repository
public class RoleDaoJpa extends GenericDaoJpa<Role, Long> implements RoleDao {

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

    public Role findByRoleName(String roleName) {
        TypedQuery<Role> q = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :roleName", Role.class);
        q.setParameter("roleName", roleName);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
