package drdm.school.pia.manager;

import drdm.school.pia.dao.RoleDao;
import drdm.school.pia.dao.UserDao;
import drdm.school.pia.domain.Role;
import drdm.school.pia.domain.User;
import drdm.school.pia.domain.UserValidationException;
import drdm.school.pia.utils.Encoder;
import drdm.school.pia.utils.StringGenerator;
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
public class DefaultUserManager implements UserManager {

    private UserDao userDao;
    private RoleDao roleDao;
    private Encoder encoder;
    private StringGenerator stringGenerator;
    @Value("${username.length}")
    private int usernameLength;
    @Value("#{'${user.roles}'.split(',')}")
    private List<String> permittedRoles;

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

    public StringGenerator getStringGenerator() { return stringGenerator; }
    @Autowired
    public void setStringGenerator(StringGenerator generator) { this.stringGenerator = generator; }

    @Override
    public boolean authenticate(String username, String password) {
        User u = userDao.findByUsername(username);
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

        newUser.setUsername(stringGenerator.generate(8));

        if (roleDao.findByRoleName(newUser.getRoleName()) != null && permittedRoles.contains(newUser.getRoleName())) {
            newUser.setRole(roleDao.findByRoleName(newUser.getRoleName()));
        } else if (roleDao.findByRoleName(newUser.getRoleName()) == null && permittedRoles.contains(newUser.getRoleName())){
            newUser.setRole(newUser.getRoleName());
        } else {
            throw new UserValidationException("Role is not in the list of permitted roles! Please contact administrator.");
        }
        newUser.validate();

        User existinCheck = userDao.findByUsername(newUser.getUsername());
        if(existinCheck != null) {
            stringGenerator.generate(usernameLength);
            //throw new UserValidationException("Username already taken!");
        }

        newUser.setPassword(encoder.encode(newUser.getPassword()));



        userDao.save(newUser);
    }
}
