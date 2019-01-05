package drdm.school.pia.manager.implementation;

import drdm.school.pia.dao.UserDao;
import drdm.school.pia.domain.entities.User;
import drdm.school.pia.domain.exceptions.UserValidationException;
import drdm.school.pia.dto.implementation.UsersFetch;
import drdm.school.pia.manager.AccountManager;
import drdm.school.pia.manager.CardManager;
import drdm.school.pia.manager.UserManager;
import drdm.school.pia.manager.RoleManager;
import drdm.school.pia.utils.Encoder;
import drdm.school.pia.utils.StringGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

/**
 * {@inheritDoc}
 * Implementation of UserManager interface
 * @author Michal Drda
 */
@Service
@Transactional
public class DefaultUserManager implements UserManager {

    /**
     * Initialization of userDao
     */
    private UserDao userDao;
    /**
     * Initialization of encoder used for encoding passwords
     */
    private Encoder encoder;
    /**
     * Initialization of roleManager to operate roles of user
     */
    private RoleManager roleManager;
    /**
     * Initialization of cardManager to operate cards of user
     */
    private CardManager cardManager;
    /**
     * Initialization of accountManager to operate account of the user
     */
    private AccountManager accountManager;
    /**
     * Initialization of the stringGenerator to generate username
     */
    private StringGenerator stringGenerator;
    /**
     * Logger used for logging of important events
     */
    final static Logger logger = Logger.getLogger(DefaultUserManager.class);
    /**
     * Defined length of user name
     */
    @Value("${username.length}")
    private int usernameLength;

    /**
     * Default constructor
     */
    public DefaultUserManager(){

    }

    /**
     * Constructor of the class
     * @param userDao provided userDao
     * @param encoder provided encoder
     */
    public DefaultUserManager(UserDao userDao, Encoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    /**
     * Getter for a roleManager
     * @return found role manager
     */
    public RoleManager getRoleManager() { return roleManager; }

    /**
     * Setter for a RoleManager
     * @param roleManager provided roleManager
     */
    @Autowired
    public void setRoleManager(RoleManager roleManager) { this.roleManager = roleManager; }

    /**
     * Setter for an accountManager
     * @param accountManager provided accountManager
     */
    @Autowired
    public void setAccountManager(AccountManager accountManager) { this.accountManager = accountManager; }

    /**
     * Getter for an accountManager
     * @return accountManager
     */
    public AccountManager getAccountManager() {
        return accountManager;
    }

    /**
     * Getter for a card manager
     * @return card manager
     */
    public CardManager getCardManager() { return cardManager; }

    /**
     * Setter for a card manager
     * @param cardManager provided card manager
     */
    @Autowired
    public void setCardManager(CardManager cardManager) { this.cardManager = cardManager; }

    /**
     * Getter for an user dao
     * @return user dao
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * Setter for an user dao
     * @param userDao provided user dao
     */
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Getter for an encoder
     * @return encoder
     */
    public Encoder getEncoder() {
        return encoder;
    }

    /**
     * Setter for an encoder
     * @param encoder provided encoder
     */
    @Autowired
    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Getter for an string Generator
     * @return string generator
     */
    public StringGenerator getStringGenerator() { return stringGenerator; }

    /**
     * Setter of the String generator
     * @param generator provided string generator
     */
    @Autowired
    public void setStringGenerator(StringGenerator generator) { this.stringGenerator = generator; }

    /**
     * {@inheritDoc}
     * Method used for authentication of the user with it's username and password
     */
    @Override
    public boolean authenticate(String username, String password) {
        User u = userDao.findByUsername(username);
        return u != null && encoder.validate(password, u.getPassword());
    }

    /**
     * {@inheritDoc}
     * Method used for registering of the user to the system
     */
    @Override
    public void register(User newUser, String username) throws UserValidationException {
        logger.info("User register started...");
        if (!newUser.isNew()) {
            throw new RuntimeException("User already exists, use save method for updates!");
        }

        // Because of the test - it can't read spring properties parameters, so we set default 8 in case that value is 0 (0 length would not be permitted either)
        if (usernameLength == 0) {
            usernameLength = 8;
        }

        if (username == null || username == "") {
            newUser.setUsername(stringGenerator.generate(usernameLength));
            logger.info("Username generated: " + newUser.getUsername());
        } else {
            newUser.setUsername(username);
        }

        roleManager.addRoleToUser(newUser, newUser.getRoleName());

        newUser.validate();

        User usernameExistingCheck = userDao.findByUsername(newUser.getUsername());

        if (usernameExistingCheck != null) {
            stringGenerator.generate(usernameLength);
        }


        User emailExistingCheck = userDao.findByEmail(newUser.getEmail());

        if (emailExistingCheck != null) {
            throw new UserValidationException("E-mail is already in use, please fill another one!");
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

    /**
     * {@inheritDoc}
     * Method used for updating of the password of the user
     */
    @Override
    public void updatePassword(String oldPwd, String newPwd, String username) throws UserValidationException {
        logger.info("Changing password started...");
        User user = userDao.findByUsername(username);

        if (!encoder.validate(oldPwd, user.getPassword())) {
            throw new UserValidationException("Incorrect old PIN entered!");
        } else if(encoder.validate(newPwd, user.getPassword())) {
            throw new UserValidationException("New PIN entered is the same as current PIN used!");
        } else {
            user.setPassword(encoder.encode(newPwd));
        }
    }

    /**
     * {@inheritDoc}
     * Method used for updating of the information for the user
     */
    @Override
    public void updateUserInfo(String firstname, String lastname, String email, String gender, String address, String city, String zip, User user) throws UserValidationException {
        try {

            User emailExistingCheck = userDao.findByEmail(email);

            if (null != emailExistingCheck && emailExistingCheck.getEmail().equals(email) && !emailExistingCheck.getUsername().equals(user.getUsername())) {
                throw new UserValidationException("E-mail is already in use by another user, please fill another one!");
            }

            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setGender(gender);
            user.setAddress(address);
            user.setCity(city);
            user.setZip(zip);
            userDao.removeFromContext(user.getUsername());
            userDao.save(user);
        } catch (Exception e) {
            throw new UserValidationException("Something went wrong while updating user!");
        }
    }

    /**
     * {@inheritDoc}
     * Method used for accessing userDao to find a user by it's username
     */
    @Override
    public User findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * {@inheritDoc}
     * Method used for accessing userDao to find a user by his account number and bank code
     */
    @Override
    public User findUserByAccount(String accountNumber, String bankCode) {
        return userDao.findByAccountNo(accountNumber, bankCode);
    }

    /**
     * {@inheritDoc}
     * Method used for removal of the user
     */
    @Override
    public void removeUser(String username) {
        userDao.delete(username);
        logger.info("User " + username + " deleted!");
    }

    /**
     * {@inheritDoc}
     * Finds an user by email
     */
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * {@inheritDoc}
     * Fetches all users with role USER from DB
     */
    public ArrayList<UsersFetch> fetchAllUsers() {
        ArrayList<User> users = (ArrayList) userDao.fetchAllUsers();
        // Remove null values fetched
        while(users.remove(null)) {
            logger.debug("removed null");
        }

        // Initialize transactions ArrayList
        ArrayList<UsersFetch> usersFetch = new ArrayList<>();

        if (users.size() > 0) {
            for (int i = 0; i < (users.size()); i++) {
                UsersFetch userFetched = new UsersFetch();
                userFetched.setId(Integer.toString(i+1));
                userFetched.setFirstname(users.get(i).getFirstname());
                userFetched.setLastname(users.get(i).getLastname());
                userFetched.setEmail(users.get(i).getEmail());
                userFetched.setGender(users.get(i).getGender());
                userFetched.setBirthid(users.get(i).getBirthId());
                userFetched.setAccount(users.get(i).getAccount().getNumber() + "/" + users.get(i).getAccount().getBank());

                usersFetch.add(userFetched);
            }

        } else {
            logger.info("No users found!");
        }

        if (usersFetch.size() > 0) {
            return usersFetch;
        } else {
            return null;
        }
    }

}
