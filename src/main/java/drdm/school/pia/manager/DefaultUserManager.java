package drdm.school.pia.manager;

import drdm.school.pia.dao.UserDao;
import drdm.school.pia.domain.Role;
import drdm.school.pia.domain.User;
import drdm.school.pia.domain.UserValidationException;
import drdm.school.pia.utils.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Michal Drda
 */
@Service
public class DefaultUserManager implements UserManager {

    private UserDao userDao;
    private Encoder encoder;

    @Autowired
    public DefaultUserManager(UserDao userDao, Encoder encoder) {
        this.userDao = userDao;
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
