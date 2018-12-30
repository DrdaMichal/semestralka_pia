package drdm.school.pia.config;

import drdm.school.pia.dao.UserDao;
import drdm.school.pia.domain.entities.User;
import drdm.school.pia.domain.exceptions.UserValidationException;
import drdm.school.pia.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class Loader implements ApplicationListener<ContextRefreshedEvent> {

    private UserDao userDao;
    private UserManager userManager;

    public Loader(UserDao userDao, UserManager userManager) {
        this.userDao = userDao;
        this.userManager = userManager;
    }

    public UserDao getUserDao() {
        return userDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserManager getUserManager() { return userManager; }
    @Autowired
    public void setUserManager(UserManager userManager) { this.userManager = userManager; }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (null == userDao.findByUsername("Admin001")) {
            try {
                userManager.register(new User("1234", "ADMIN", "Admin", "Administrator", "admin@email.com", "Administrator street 101010", "Admintown", "101010", "404", "male"), "Admin001");
            } catch (UserValidationException e) {
                e.printStackTrace();
            }
        }
        if (null == userDao.findByUsername("User0001")) {
            try {
                userManager.register(new User("0001", "USER", "First", "User", "user1@email.com", "User street 1", "Usertown", "111111", "1234567890", "male"), "User0001");
            } catch (UserValidationException e) {
                e.printStackTrace();
            }
        }
        if (null == userDao.findByUsername("User0002")) {
            try {
                userManager.register(new User("0002", "USER", "Second", "User", "user2@email.com", "User street 1", "Usertown", "111111", "1234567890", "female"), "User0002");
            } catch (UserValidationException e) {
                e.printStackTrace();
            }
        }
    }
}