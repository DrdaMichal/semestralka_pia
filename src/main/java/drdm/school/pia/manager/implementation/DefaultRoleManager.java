package drdm.school.pia.manager.implementation;

import drdm.school.pia.dao.CardDao;
import drdm.school.pia.dao.RoleDao;
import drdm.school.pia.domain.Role;
import drdm.school.pia.domain.User;
import drdm.school.pia.domain.UserValidationException;
import drdm.school.pia.manager.RoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Michal Drda
 */
@Service
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class DefaultRoleManager implements RoleManager {

    private RoleDao roleDao;
    @Value("#{'${user.roles}'.split(',')}")
    private List<String> permittedRoles;

    public DefaultRoleManager() {

    }

    public DefaultRoleManager(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    public RoleDao getRoleDao() {
        return roleDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void addRoleToUser(User user, String roleName) throws UserValidationException {
        if (roleDao.findByRoleName(roleName) != null && permittedRoles.contains(roleName)) {
            user.setRole(roleDao.findByRoleName(roleName));
        } else if (roleDao.findByRoleName(roleName) == null && permittedRoles.contains(roleName)) {
            user.setRole(roleName);
        } else {
            throw new UserValidationException("Role is not in the list of permitted roles! Please contact administrator.");
        }
    }

    public Role getUserRoleByUsername(String username) {
        return roleDao.findByUserName(username);
    }

}
