package drdm.school.pia.manager.implementation;

import drdm.school.pia.dao.UserDao;
import drdm.school.pia.domain.User;
import drdm.school.pia.domain.UserValidationException;
import drdm.school.pia.manager.CardManager;
import drdm.school.pia.manager.UserManager;
import drdm.school.pia.manager.RoleManager;
import drdm.school.pia.utils.Encoder;
import drdm.school.pia.utils.LongGenerator;
import drdm.school.pia.utils.StringGenerator;
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
    private StringGenerator stringGenerator;
    private LongGenerator numberGenerator;

    @Value("${username.length}")
    private int usernameLength;
    @Value("${accountNo.length}")
    private int accountNoLength;
    @Value("${bankcode}")
    private String bankcode;

    public DefaultUserManager(){

    }

    public DefaultUserManager(UserDao userDao, Encoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    public RoleManager getRoleManager() { return roleManager; }

    @Autowired
    public void setRoleManager(RoleManager roleManager) { this.roleManager = roleManager; }

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

    public LongGenerator getIntGenerator() { return numberGenerator; }
    @Autowired
    public void setIntGenerator(LongGenerator generator) { this.numberGenerator = generator; }

    @Override
    public boolean authenticate(String username, String password) {
        User u = userDao.findByUsername(username);
        return u != null && encoder.validate(password, u.getPassword());
    }

    @Override
    public void register(User newUser) throws UserValidationException {
        if (!newUser.isNew()) {
            throw new RuntimeException("User already exists, use save method for updates!");
        }

        newUser.setUsername(stringGenerator.generate(usernameLength));
        newUser.setAccount(numberGenerator.generate(accountNoLength) + "/" + bankcode);
        roleManager.addRoleToUser(newUser, newUser.getRoleName());

        newUser.validate();

        User usernameExistingCheck = userDao.findByUsername(newUser.getUsername());
        User accountExistingCheck = userDao.findByAccountNo(newUser.getAccount());

        if (usernameExistingCheck != null) {
            stringGenerator.generate(usernameLength);
        }
        if (accountExistingCheck != null) {
            numberGenerator.generateBankAccount(accountNoLength, bankcode);
        }

        cardManager.createCard(newUser);

        newUser.setPassword(encoder.encode(newUser.getPassword()));
        userDao.save(newUser);
    }
}
