package drdm.school.pia.manager.implementation;

import drdm.school.pia.dao.AccountDao;
import drdm.school.pia.domain.entities.Account;
import drdm.school.pia.domain.exceptions.PaymentValidationException;
import drdm.school.pia.domain.entities.User;
import drdm.school.pia.manager.AccountManager;
import drdm.school.pia.utils.LongGenerator;
import drdm.school.pia.utils.StringGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @inheritDoc
 * Implementation of AccountManager interface
 */
@Service
@Transactional
public class DefaultAccountManager implements AccountManager {

    /**
     * Account dao initialisation
     */
    private AccountDao accountDao;
    /**
     * String generator initialization
     * Used for generating random Strings
     */
    private StringGenerator stringGenerator;
    /**
     * Long generator initialization
     * Used for generating random numbers
     */
    private LongGenerator numberGenerator;

    /**
     * Logger initialization
     * Used for logging everything useful
     */
    final static Logger logger = Logger.getLogger(DefaultAccountManager.class);

    /**
     * Length of account
     * Taken from properties
     */
    @Value("${accountNo.length}")
    private int accountNoLength;
    /**
     * Default value bank code
     * Taken from properties
     */
    @Value("${bankcode}")
    private String bankcode;
    /**
     * Default ballance assigned to the account
     * Taken from properties
     */
    @Value("${default.balance}")
    private Long defaultBalance;

    /**
     * Default constructor for DefaultAccountManager
     */
    public DefaultAccountManager() {

    }

    /**
     * Constructor of DefaultAccountManager with accountDao param
     * Used for DI possibility
     * @param accountDao account dao provided
     */
    public DefaultAccountManager(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * Getter for String generator object
     * @return String generator
     */
    public StringGenerator getStringGenerator() { return stringGenerator; }

    /**
     * Setter for String generator object
     * @param generator String Generator provided
     */
    @Autowired
    public void setStringGenerator(StringGenerator generator) { this.stringGenerator = generator; }

    /**
     * Getter for Long generator
     * @return Number generator from this manager
     */
    public LongGenerator getIntGenerator() { return numberGenerator; }

    /**
     * Setter for Long generator
     * @param generator long generator provided
     */
    @Autowired
    public void setLongGenerator(LongGenerator generator) { this.numberGenerator = generator; }

    /**
     * Setter for Account Dao
     * @param accountDao account dao provided
     */
    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * @inheritDoc
     * Implementation of createAccount method of AccountManager interface
     * Used for creating account
     */
    @Override
    public void createAccount(User user) {
        Account newAccount = new Account();
        newAccount.setNumber(Long.toString(numberGenerator.generate(accountNoLength)));
        newAccount.setBank(bankcode);
        newAccount.setBalance(defaultBalance);

        // No need to check for bank code as it's always the same
        Account accountExistingCheck = accountDao.findByAccountNumber(newAccount.getNumber());
        if (accountExistingCheck != null) {
            numberGenerator.generate(accountNoLength);
        }

        user.setAccount(newAccount);
        accountDao.save(newAccount);
        logger.info("Account created for user<" + user.getUsername() + ">: " + user.getAccount().toString());

    }

    /**
     * @inheritDoc
     * Implementation of updateBalance method of AccountManager interface
     * Used for updating of balance of provided account
     */
    @Override
    public void updateBalance(Account account, double valueOfChange) throws PaymentValidationException {
        if(valueOfChange < 0 && account.getBalance() < valueOfChange) {
            logger.info("Not enough money on the account " + account.getNumber() + "/" + account.getBank() + "!");
            throw new PaymentValidationException("On the account " + account.getNumber() + "/" + account.getBank() + " is not enough money!");
        } else if (valueOfChange != 0) {
            // Value to be added or substracted (as the valueOfChange has + or - sign, it's always balance + valueOfChange
            logger.info("Current account " + account.getNumber() + "/" + account.getBank() + " balance: " + account.getBalance());
            account.setBalance(account.getBalance() + valueOfChange);
        }
    }

    /**
     * @InheritDoc
     * Implementation of findAccountByUsername method of AccountManager interface
     * Used for getting account assigned to User with given username
     */
    @Override
    public Account findAccountByUsername(String username) {
        return accountDao.findByUserName(username);
    }

}
