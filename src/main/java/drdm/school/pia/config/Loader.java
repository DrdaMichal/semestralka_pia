package drdm.school.pia.config;

import drdm.school.pia.dao.PaymentDao;
import drdm.school.pia.dao.UserDao;
import drdm.school.pia.domain.entities.Payment;
import drdm.school.pia.domain.entities.User;
import drdm.school.pia.domain.exceptions.PaymentValidationException;
import drdm.school.pia.domain.exceptions.UserValidationException;
import drdm.school.pia.manager.PaymentManager;
import drdm.school.pia.manager.UserManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Class for loading data to the database, used just for creating data in database, so the lecturer can easily test the application.
 * This class should not really be a part of this application in general, but it is here to ease the process.
 *
 * @author Michal Drda
 */
@Service
@Transactional
public class Loader implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * Attribute used for setting DB (create == fillup, update == no action)
     */
    @Value("${db.state}")
    private String fillup;

    /**
     * Logger used for logging of the events while necessary.
     */
    final static Logger logger = Logger.getLogger(Loader.class);

    /**
     * UserDao initialisation
     */
    private UserDao userDao;
    /**
     * UserManager initialisation
     */
    private UserManager userManager;
    /**
     * PaymentDao initialisation
     */
    private PaymentDao paymentDao;
    /**
     * PaymentManager initialisation
     */
    private PaymentManager paymentManager;

    /**
     * Constructor used by Spring for wiring
     * @param userDao provided userDao object
     * @param userManager provided userManager object
     * @param paymentDao provided paymentDao object
     * @param paymentManager provided paymentManager object
     */
    public Loader(UserDao userDao, UserManager userManager, PaymentDao paymentDao, PaymentManager paymentManager) {
        this.userDao = userDao;
        this.userManager = userManager;
        this.paymentDao = paymentDao;
        this.paymentManager = paymentManager;
    }

    /**
     * Getter for UserDao
     * @return userDao dao
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * Setter for UserDao
     * @param userDao userDao dao
     */
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Getter for UserManager
     * @return userManager manager
     */
    public UserManager getUserManager() { return userManager; }

    /**
     * Setter for UserManager
     * @param userManager userManager manager
     */
    @Autowired
    public void setUserManager(UserManager userManager) { this.userManager = userManager; }

    /**
     * Getter for PaymentDao
     * @return paymentDao dao
     */
    public PaymentDao getPaymentDao() {
        return paymentDao;
    }

    /**
     * Setter for PaymentDao
     * @param paymentDao paymentDao dao
     */
    @Autowired
    public void setPaymentDao(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    /**
     * Getter for PaymentManager
     * @return paymentManager
     */
    public PaymentManager getPaymentManager() { return paymentManager; }

    /**
     * Setter for PaymentManager
     * @param paymentManager paymentManager manager
     */
    @Autowired
    public void setPaymentManager(PaymentManager paymentManager) { this.paymentManager = paymentManager; }

    /**
     * Should not be part of this application, used just for creating data in database, so the lecturer can easily test the application.
     * @param event provided by spring
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (fillup.equals("create")) {
            try {
                userManager.register(new User("0001", "USER", "First", "User", "user1@email.com", "User street 1", "Usertown", "111111", "1234567890", "male"), "User0001");
            } catch (UserValidationException e) {
                e.printStackTrace();
            }
            try {
                userManager.register(new User("0002", "USER", "Second", "User", "user2@email.com", "User street 1", "Usertown", "111111", "1234567890", "female"), "User0002");
            } catch (UserValidationException e) {
                e.printStackTrace();
            }
            try {
                userManager.register(new User("1234", "ADMIN", "Admin", "Administrator", "admin@email.com", "Administrator street 101010", "Admintown", "101010", "404", "male"), "Admin001");
            } catch (UserValidationException e) {
                e.printStackTrace();
            }
            User user0001 = userManager.findUserByUsername("User0001");
            User user0002 = userManager.findUserByUsername("User0002");
            for (int i = 0; i < 13; i++) {
                //new Payment(selectedTemplate, sendTo, bankCode, recPreAccount, account.getNumber(), account.getBank(), "", vs, cs, ss, recipientMessage, myMessage, amount, currency, transactionDateDate, template), req.getSession().getAttribute("user").toString()
                try {
                    paymentManager.createPayment(new Payment("", user0002.getAccount().getNumber(), user0002.getAccount().getBank(), "", user0001.getAccount().getNumber(), user0001.getAccount().getBank(), "", Integer.toString(i), Integer.toString(i), Integer.toString(i), ("Recipient note " + i), ("My note " + i), Integer.toString(i + 1), (i % 2 == 1 ? "CZK" : "EUR"), new Date(), (i % 5 == 1 ? ("template " + i) : null)), "User0001");
                } catch (PaymentValidationException e) {
                    logger.warn("Payment [" + i + "]could not been created!");
                }
                try {
                    paymentManager.createPayment(new Payment("", user0001.getAccount().getNumber(), user0001.getAccount().getBank(), "", user0002.getAccount().getNumber(), user0002.getAccount().getBank(), "", Integer.toString(i), Integer.toString(i), Integer.toString(i), ("Recipient note " + i), ("My note " + i), Integer.toString(i + 1), (i % 2 == 1 ? "CZK" : "EUR"), new Date(), (i % 4 == 1 ? ("template " + i) : null)), "User0002");
                } catch (PaymentValidationException e) {
                    logger.warn("Payment [" + i + "]could not been created!");
                }
            }
        }
    }
}