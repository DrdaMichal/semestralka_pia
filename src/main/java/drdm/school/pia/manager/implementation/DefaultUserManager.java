package drdm.school.pia.manager.implementation;

import drdm.school.pia.dao.UserDao;
import drdm.school.pia.domain.User;
import drdm.school.pia.domain.UserValidationException;
import drdm.school.pia.manager.AccountManager;
import drdm.school.pia.manager.CardManager;
import drdm.school.pia.manager.UserManager;
import drdm.school.pia.manager.RoleManager;
import drdm.school.pia.utils.Encoder;
import drdm.school.pia.utils.StringGenerator;
import drdm.school.pia.web.servlet.spring.Login;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private Encoder encoder;
    private RoleManager roleManager;
    private CardManager cardManager;
    private AccountManager accountManager;
    private StringGenerator stringGenerator;

    final static Logger logger = Logger.getLogger(DefaultUserManager.class);

    @Value("${username.length}")
    private int usernameLength;

    public DefaultUserManager(){

    }

    public DefaultUserManager(UserDao userDao, Encoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    public RoleManager getRoleManager() { return roleManager; }

    @Autowired
    public void setRoleManager(RoleManager roleManager) { this.roleManager = roleManager; }

    @Autowired
    public void setAccountManager(AccountManager accountManager) { this.accountManager = accountManager; }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public CardManager getCardManager() { return cardManager; }

    @Autowired
    public void setCardManager(CardManager cardManager) { this.cardManager = cardManager; }

    public UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
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

/*

    public void createDefaultUsers() throws UserValidationException {

        User Admin001 = new User("1234", "ADMIN", "Admin", "Administrator", "admin@email.com", "Administrator street 101010", "Admintown", "101010", "404", "male");
        register(Admin001);
        //Admin001.setUsername("Admin001");

        User User0001 = new User("0001", "USER", "First", "User", "user1@email.com", "User street 1", "Usertown", "111111", "1234567890", "male");
        register(User0001);
        //Admin001.setUsername("User0001");

        User User0002 = new User("0001", "USER", "Second", "User", "user2@email.com", "User street 1", "Usertown", "111111", "1234567890", "female");
        register(User0002);
        //Admin001.setUsername("User0002");

    }
*/

    @Override
    public void register(User newUser) throws UserValidationException {
        logger.info("User register started...");
        if (!newUser.isNew()) {
            throw new RuntimeException("User already exists, use save method for updates!");
        }

        newUser.setUsername(stringGenerator.generate(usernameLength));
        logger.info("Username generated: " + newUser.getUsername());

        roleManager.addRoleToUser(newUser, newUser.getRoleName());

        newUser.validate();

        User usernameExistingCheck = userDao.findByUsername(newUser.getUsername());

        if (usernameExistingCheck != null) {
            stringGenerator.generate(usernameLength);
        }

        if (!newUser.getRole().getName().equals("ADMIN")) {
            accountManager.createAccount(newUser);
            cardManager.createCard(newUser, newUser.getAccount());
        }
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        logger.info("Password generated for user<" + newUser.getUsername() + ">");

        userDao.save(newUser);
        logger.info("User saved: " + newUser.toString());
    }
}
