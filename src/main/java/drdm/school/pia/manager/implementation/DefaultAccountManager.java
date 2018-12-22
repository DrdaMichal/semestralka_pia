package drdm.school.pia.manager.implementation;

import drdm.school.pia.dao.AccountDao;
import drdm.school.pia.domain.Account;
import drdm.school.pia.domain.Card;
import drdm.school.pia.domain.User;
import drdm.school.pia.manager.AccountManager;
import drdm.school.pia.utils.LongGenerator;
import drdm.school.pia.utils.StringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

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

    @Value("${accountNo.length}")
    private int accountNoLength;
    @Value("${bankcode}")
    private String bankcode;

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
        newAccount.setNumber(numberGenerator.generate(accountNoLength) + "/" + bankcode);
        newAccount.setBlocked(false);
        newAccount.setBalance(new Long(0));

        Account accountExistingCheck = accountDao.findByAccountNumber(newAccount.getNumber());
        if (accountExistingCheck != null) {
            numberGenerator.generateBankAccount(accountNoLength, bankcode);
        }

        user.setAccount(newAccount);
        accountDao.save(newAccount);

    }

}