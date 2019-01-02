package drdm.school.pia.manager.implementation;

import drdm.school.pia.dao.PaymentDao;
import drdm.school.pia.dao.UserDao;
import drdm.school.pia.domain.entities.Account;
import drdm.school.pia.domain.entities.Payment;
import drdm.school.pia.domain.exceptions.PaymentValidationException;
import drdm.school.pia.domain.entities.User;
import drdm.school.pia.dto.implementation.Transaction;
import drdm.school.pia.manager.AccountManager;
import drdm.school.pia.manager.PaymentManager;
import drdm.school.pia.manager.UserManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;


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
    private UserManager userManager;
    @Value("${bankcode}")
    private String bankcode;
    @Value("${accountNo.length}")
    private int accountNoLength;
    @Value("${default.pattern.date}")
    private String transactionDatePattern;
    @Value("${default.currency}")
    private String defaultCurrency;
    @Value("#{'${currency.couses}'.split(',')}")
    private List<String> currenciesCourses;

    final static Logger logger = Logger.getLogger(DefaultPaymentManager.class);

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

    @Autowired
    public void setUserManager(UserManager userManager) { this.userManager = userManager; }

    public UserManager getUserManager() {
        return userManager;
    }

    public void createPayment(Payment newPayment, String username) throws PaymentValidationException {
        logger.info("Payment creation started...");
        User user = userManager.findUserByUsername(username);
        // Check that receiving user exists in the bank
        User receivingUser = null;
        if (newPayment.getRecipientBankCode().equals(bankcode) && newPayment.getRecipientPreAccountNumber().isEmpty()) {
            receivingUser = userManager.findUserByAccount(newPayment.getRecipientAccount(), newPayment.getRecipientBankCode());
        }
        newPayment.setCreated(new java.util.Date());
        newPayment.setSenderAccount(user.getAccount().getNumber());
        newPayment.setSenderBankCode(user.getAccount().getBank());
        newPayment.setSenderPreAccountNumber("");

        double course = 1;

        if (!newPayment.getCurrency().equals(defaultCurrency)) {
            for (int i = 0; i<currenciesCourses.size(); i++ ) {
                if (currenciesCourses.get(i).split(":")[0].equals(newPayment.getCurrency())) {
                    course = Double.parseDouble(currenciesCourses.get(i).split(":")[1]);
                }
            }
        }
        newPayment.setAmount(course*newPayment.getAmount());
        newPayment.setCurrency(defaultCurrency);

        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Date test = c.getTime();

        if (newPayment.getRecipientBankCode().equals(bankcode) && newPayment.getRecipientAccount().equals(user.getAccount().getNumber())) {
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
        accountManager.updateBalance(user.getAccount(), -1 * (newPayment.getAmount()));

        if (newPayment.getRecipientBankCode().equals(bankcode) && newPayment.getRecipientAccount().length() == accountNoLength && newPayment.getRecipientPreAccountNumber().isEmpty() && receivingUser != null) {
            accountManager.updateBalance(receivingUser.getAccount(), newPayment.getAmount());
        }

        // Remove template from the old payment in case that new one with the same name to be created
        if (null != newPayment.getTemplate()) {
            Payment existingTemplate = paymentDao.findPaymentByTemplate(user.getUsername(), newPayment.getTemplate());
            if (null != existingTemplate) {
                existingTemplate.setTemplate(null);
            }
        }

        paymentDao.save(newPayment);
        logger.info("Payment saved: " + newPayment.toString());
        // to be uncommented in case that future payments will be implemented
        /*}*/
    }

    public ArrayList<String> loadPaymentTemplate(String username) {
        ArrayList<Payment> paymentTemplates =(ArrayList) paymentDao.findTemplatesByUsername(username);
        ArrayList<String> templates = new ArrayList<>();
        for (int i=0; i<paymentTemplates.size(); i++) {
            templates.add(paymentTemplates.get(i).getTemplate());
        }
        logger.debug("Fetched following templates: " + templates.toString());
        return templates;
    }

    public Payment loadPaymentByTemplate(String username, String template) {
        return paymentDao.findPaymentByTemplate(username, template);
    }

    @Override
    public ArrayList<Transaction> findTransactionsForUsername(String username) {
        // Get Account for matching username parameter
        Account account = accountManager.findAccountByUsername(username);
        // Load Payments for matching username parameter
        ArrayList<Payment> payments = (ArrayList) paymentDao.findTransactionsByAccount(account.getNumber(), account.getBank());
        // Remove null values fetched
        while(payments.remove(null)) {
            logger.info("removed null");
        }
        // Initialize transactions ArrayList
        ArrayList<Transaction> transactions = new ArrayList<>();
        // Define date format
        SimpleDateFormat dateFormat = new SimpleDateFormat(transactionDatePattern);

        if (payments.size() > 0) {
            // Fill ArrayList with transactions for the user matching username parameter
            for (int i = 0; i < (payments.size()); i++) {
                Transaction transaction = new Transaction();
                transaction.setId(Integer.toString(i + 1));
                transaction.setDate(dateFormat.format(payments.get(i).getTransactionDate()));
                if ((payments.get(i).getSenderAccount().equals(account.getNumber())) && payments.get(i).getSenderBankCode().equals(account.getBank())) {
                    transaction.setDirection("Out");
                    transaction.setAccount(((!payments.get(i).getRecipientPreAccountNumber().isEmpty() && "" != payments.get(i).getRecipientPreAccountNumber()) ? (payments.get(i).getRecipientPreAccountNumber() + "-" ) :"") + payments.get(i).getRecipientAccount() + "/" + payments.get(i).getRecipientBankCode());
                    transaction.setYourMessage(!payments.get(i).getSenderMessage().isEmpty() ? payments.get(i).getSenderMessage() : "-");
                } else {
                    transaction.setDirection("In");
                    transaction.setAccount(((!payments.get(i).getSenderPreAccountNumber().isEmpty() && "" != payments.get(i).getSenderPreAccountNumber()) ? (payments.get(i).getSenderPreAccountNumber() + "-" ) :"") + payments.get(i).getSenderAccount() + "/" + payments.get(i).getSenderBankCode());
                    transaction.setYourMessage("-");
                }
                transaction.setAmount(String.format("%.3f", payments.get(i).getAmount()) + " " + payments.get(i).getCurrency());
                transaction.setVs(!payments.get(i).getVs().isEmpty() ? payments.get(i).getVs() : "-");
                transaction.setCs(!payments.get(i).getCs().isEmpty() ? payments.get(i).getCs() : "-");
                transaction.setSs(!payments.get(i).getSs().isEmpty() ? payments.get(i).getSs() : "-");
                transaction.setTheirMessage(!payments.get(i).getRecipientMessage().isEmpty() ? payments.get(i).getRecipientMessage() : "-");

                transactions.add(transaction);
            }
        } else {
            logger.info("No transactions for account " + account.getNumber() + "/" + account.getBank());
        }

        if (transactions.size() > 0) {
            return transactions;
        } else {
            return null;
        }
    }

}
