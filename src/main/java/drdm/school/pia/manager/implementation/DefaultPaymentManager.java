package drdm.school.pia.manager.implementation;

import drdm.school.pia.dao.PaymentDao;
import drdm.school.pia.dao.UserDao;
import drdm.school.pia.domain.Payment;
import drdm.school.pia.domain.PaymentValidationException;
import drdm.school.pia.domain.User;
import drdm.school.pia.manager.AccountManager;
import drdm.school.pia.manager.PaymentManager;
import drdm.school.pia.web.servlet.spring.Login;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;


/**
 * @author Michal Drda
 */
@Service
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class DefaultPaymentManager implements PaymentManager {

    private PaymentDao paymentDao;
    private UserDao userDao;
    private AccountManager accountManager;
    @Value("${bankcode}")
    private String bankcode;
    @Value("${accountNo.length}")
    private int accountNoLength;

    final static Logger logger = Logger.getLogger(Login.class);

    public DefaultPaymentManager() {

    }

    public DefaultPaymentManager(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public PaymentDao getPaymentDao() {
        return paymentDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setPaymentDao(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Autowired
    public void setAccountManager(AccountManager accountManager) { this.accountManager = accountManager; }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public void createPayment(Payment newPayment, String username) throws PaymentValidationException {
        logger.info("Payment creation started...");
        User user = userDao.findByUsername(username);
        // Check that receiving user exists in the bank
        User receivingUser = userDao.findByAccountNo(newPayment.getSendTo());
        newPayment.setCreated(new java.util.Date());
        newPayment.setAccount(user.getAccount());

        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Date test = c.getTime();

        if (newPayment.getBankCode().equals(bankcode) && newPayment.getSendTo().equals(user.getAccount().getNumber())) {
            logger.info("Receiving account can't be equal to senders account!");
            throw new PaymentValidationException("Receiving account can't be equal to senders account!");
        }

        if (newPayment.getTransactionDate().before(c.getTime())) {
            logger.info("The date selected is in the past!");
            throw new PaymentValidationException("The date selected is in the past!");
        }
        // to be uncommented in case that future payments will be implemented
        /* else if (newPayment.getTransactionDate().after(c.getTime())) {
            // In case that the payment should be executed in future (not instantly).
            // Some logic here..
        } else {*/

        if (newPayment.getAmount() > user.getAccount().getBalance()) {
            logger.info("Not enough money on the account " + user.getAccount().getNumber() + "/" + user.getAccount().getBank() + "!");
            throw new PaymentValidationException("Not enough money on the account " + user.getAccount().getNumber() + "/" + user.getAccount().getBank() + "!");
        }

        newPayment.validate();
        accountManager.updateBallance(user.getAccount(), -1 * (newPayment.getAmount()));

        if (newPayment.getBankCode().equals(bankcode) && newPayment.getSendTo().length() == accountNoLength && receivingUser != null) {
            accountManager.updateBallance(receivingUser.getAccount(), newPayment.getAmount());
        }

        paymentDao.save(newPayment);
        logger.info("Payment saved: " + newPayment.toString());
        // to be uncommented in case that future payments will be implemented
        /*}*/
    }

}
