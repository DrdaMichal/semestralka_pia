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
 * @author Michal Drda
 */
@Service
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class DefaultAccountManager implements AccountManager {

    private AccountDao accountDao;
    private StringGenerator stringGenerator;
    private LongGenerator numberGenerator;

    final static Logger logger = Logger.getLogger(DefaultAccountManager.class);

    @Value("${accountNo.length}")
    private int accountNoLength;
    @Value("${bankcode}")
    private String bankcode;
    @Value("${default.balance}")
    private Long defaultBalance;

    public DefaultAccountManager() {

    }

    public DefaultAccountManager(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    public StringGenerator getStringGenerator() { return stringGenerator; }
    @Autowired
    public void setStringGenerator(StringGenerator generator) { this.stringGenerator = generator; }

    public LongGenerator getIntGenerator() { return numberGenerator; }
    @Autowired
    public void setIntGenerator(LongGenerator generator) { this.numberGenerator = generator; }

    @Autowired
    public void setUserDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void createAccount(User user) {
        Account newAccount = new Account();
        newAccount.setNumber(Long.toString(numberGenerator.generate(accountNoLength)));
        newAccount.setBank(bankcode);
        newAccount.setBlocked(false);
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

    @Override
    public void updateBallance(Account account, Long valueOfChange) throws PaymentValidationException {
        if(valueOfChange < 0 && account.getBalance() < valueOfChange) {
            logger.info("Not enough money on the account " + account.getNumber() + "/" + account.getBank() + "!");
            throw new PaymentValidationException("On the account " + account.getNumber() + "/" + account.getBank() + " is not enough money!");
        } else if (valueOfChange != 0) {
            // Value to be added or substracted (as the valueOfChange has + or - sign, it's always balance + valueOfChange
            logger.info("Current account " + account.getNumber() + "/" + account.getBank() + " balance: " + account.getBalance());
            account.setBalance(account.getBalance() + valueOfChange);
        }
    }

    @Override
    public Account findAccountByUsername(String username) {
        return accountDao.findByUserName(username);
    }


}
