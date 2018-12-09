package drdm.school.pia.manager;

import drdm.school.pia.dao.RoleDao;
import drdm.school.pia.dao.UserDao;
import drdm.school.pia.domain.Role;
import drdm.school.pia.domain.User;
import drdm.school.pia.domain.UserValidationException;
import drdm.school.pia.utils.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Michal Drda
 */
@Service
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class DefaultUserManager implements UserManager {

    private UserDao userDao;
    private RoleDao roleDao;
    private Encoder encoder;

    public DefaultUserManager(){

    }

    public DefaultUserManager(UserDao userDao, Encoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Encoder getEncoder() {
        return encoder;
    }
    @Autowired
    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public boolean authenticate(String username, String password) {
        User u = userDao.findByUsername(username);
        //System.out.println(u.toString());
        return u != null && encoder.validate(password, u.getPassword());
    }

    @Override
    public Role userRole (String username) {
        Role userRole = userDao.findByUsername(username).getRole();
        return userRole;
    }

    @Override
    public void register(User newUser) throws UserValidationException {
        if(!newUser.isNew()) {
            throw new RuntimeException("User already exists, use save method for updates!");
        }

        newUser.validate();

        User existinCheck = userDao.findByUsername(newUser.getUsername());
        if(existinCheck != null) {
            throw new UserValidationException("Username already taken!");
        }

        newUser.setPassword(encoder.encode(newUser.getPassword()));

        userDao.save(newUser);
    }
}
