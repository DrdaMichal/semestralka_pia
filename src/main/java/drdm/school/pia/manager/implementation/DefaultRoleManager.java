package drdm.school.pia.manager.implementation;

import drdm.school.pia.dao.RoleDao;
import drdm.school.pia.domain.entities.Role;
import drdm.school.pia.domain.entities.User;
import drdm.school.pia.domain.exceptions.UserValidationException;
import drdm.school.pia.manager.RoleManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * {@inheritDoc}
 * Implementation of RoleManager interface
 * @author Michal Drda
 */
@Service
@Transactional
public class DefaultRoleManager implements RoleManager {

    /**
     * Initialization of roleDao
     */
    private RoleDao roleDao;
    /**
     * Logger used for logging of imporatnt events
     */
    final static Logger logger = Logger.getLogger(DefaultRoleManager.class);

    /**
     * List of permitted roles that can be added to user
     */
    @Value("#{'${user.roles}'.split(',')}")
    private List<String> permittedRoles;

    /**
     * Default constructor
     */
    public DefaultRoleManager() {

    }

    /**
     * Constructor
     * @param roleDao provided roleDao
     */
    public DefaultRoleManager(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    /**
     * Getter of the roleDao
     * @return role dao
     */
    public RoleDao getRoleDao() {
        return roleDao;
    }

    /**
     * Setter of the roleDao
     * @param roleDao provided roleDao
     */
    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    /**
     * {@inheritDoc}
     * Method used for adding role to the user
     */
    @Override
    public void addRoleToUser(User user, String roleName) throws UserValidationException {
        if (roleDao.findByRoleName(roleName) != null && permittedRoles.contains(roleName)) {
            user.setRole(roleDao.findByRoleName(roleName));
        } else if (roleDao.findByRoleName(roleName) == null && permittedRoles.contains(roleName)) {
            user.setRole(roleName);
        } else {
            throw new UserValidationException("Role is not in the list of permitted roles! Please contact administrator.");
        }
        logger.info("Role assigned: " + user.getRole());
    }

    /**
     * {@inheritDoc}
     * Method used to find a role by user name
     */
    public Role getUserRoleByUsername(String username) {
        return roleDao.findByUserName(username);
    }

}
