package drdm.school.pia.dao.implementation;

import drdm.school.pia.dao.RoleDao;
import drdm.school.pia.domain.entities.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * JPA implementation of the RoleDao interface
 * @author Michal Drda
 */
@Repository
public class RoleDaoJpa extends GenericDaoJpa<Role, Long> implements RoleDao {

    /**
     * Constructor
     */
    public RoleDaoJpa() {
        super(Role.class);
    }

    /**
     * {@inheritDoc}
     * This is an implementation of findByUserName method in RoleDao interface
     */
    @Override
    public Role findByUserName(String username) {
        TypedQuery<Role> q = entityManager.createQuery("SELECT r FROM Role r LEFT JOIN User u ON r.id = u.role.id WHERE u.username = :username ", Role.class);
        q.setParameter("username", username);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     * This is an implementation of findByRoleName method in RoleDao interface
     */
    @Override
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
