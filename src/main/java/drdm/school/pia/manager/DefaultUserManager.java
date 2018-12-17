package drdm.school.pia.manager;

import drdm.school.pia.dao.RoleDao;
import drdm.school.pia.dao.UserDao;
import drdm.school.pia.domain.Role;
import drdm.school.pia.domain.User;
import drdm.school.pia.domain.UserValidationException;
import drdm.school.pia.utils.Encoder;
import drdm.school.pia.utils.ExpirationGenerator;
import drdm.school.pia.utils.IntGenerator;
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
    private IntGenerator numberGenerator;
    private ExpirationGenerator cardExpirationGenerator;
    @Value("${username.length}")
    private int usernameLength;
    @Value("${accountNo.length}")
    private int accountNoLength;
    @Value("${bankcode}")
    private String bankcode;
    @Value("${cardNo.length}")
    private int cardNoLength;
    @Value("${cvcNo.length}")
    private int cvcNoLength;
    @Value("${cardExpiration.months}")
    private int cardExpirationInMonthsLength;
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

    public IntGenerator getIntGenerator() { return numberGenerator; }
    @Autowired
    public void setIntGenerator(IntGenerator generator) { this.numberGenerator = generator; }

    public ExpirationGenerator getCardExpirationGenerator() { return cardExpirationGenerator; }
    @Autowired
    public void setcardExpirationGenerator(ExpirationGenerator generator) { this.cardExpirationGenerator = generator; }

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
        if (!newUser.isNew()) {
            throw new RuntimeException("User already exists, use save method for updates!");
        }

        newUser.setUsername(stringGenerator.generate(usernameLength));
        newUser.setAccount(numberGenerator.generate(accountNoLength) + "/" + bankcode);
        newUser.setCard(Integer.toString(numberGenerator.generate(cardNoLength)));
        newUser.setCvc(Integer.toString(numberGenerator.generate(cvcNoLength)));
        newUser.setCardExpiration(cardExpirationGenerator.generateExpiration(cardExpirationInMonthsLength));

        if (roleDao.findByRoleName(newUser.getRoleName()) != null && permittedRoles.contains(newUser.getRoleName())) {
            newUser.setRole(roleDao.findByRoleName(newUser.getRoleName()));
        } else if (roleDao.findByRoleName(newUser.getRoleName()) == null && permittedRoles.contains(newUser.getRoleName())) {
            newUser.setRole(newUser.getRoleName());
        } else {
            throw new UserValidationException("Role is not in the list of permitted roles! Please contact administrator.");
        }
        newUser.validate();

        User usernameExistingCheck = userDao.findByUsername(newUser.getUsername());
        User accountExistingCheck = userDao.findByAccountNo(newUser.getAccount());
        User cardExistingCheck = userDao.findByCardNo(newUser.getCard());
        // No need to check cvc for uniqueness, doesn't have to be unique

        while (usernameExistingCheck != null) {
            newUser.setUsername(stringGenerator.generate(usernameLength));
        }
        while (accountExistingCheck != null) {
            newUser.setAccount(numberGenerator.generate(accountNoLength) + "/" + bankcode);
        }
        while (cardExistingCheck != null) {
            newUser.setCard(Integer.toString(numberGenerator.generate(cardNoLength)));
        }

        newUser.setPassword(encoder.encode(newUser.getPassword()));
        userDao.save(newUser);
    }
}
