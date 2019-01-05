package drdm.school.pia.manager;

import drdm.school.pia.domain.entities.Account;
import drdm.school.pia.domain.exceptions.PaymentValidationException;
import drdm.school.pia.domain.entities.User;

import java.math.BigDecimal;

/**
 * Account manager interface - used for managing accounts
 * @author Michal Drda
 */
public interface AccountManager {

    /**
     * Creates a card for user
     * @param user provided user to own the card created
     */
    void createAccount(User user);

    /**
     * Method used to manipulate with balance of the account
     * @param account provided account which balance to be adjusted
     * @param valueOfChange provided positive or negative value to be added or substracted from the account
     * @throws PaymentValidationException in case of the error - payment is not in valid state
     */
    void updateBalance(Account account, BigDecimal valueOfChange) throws PaymentValidationException;

    /**
     * Finds an account by given user name
     * @param username provided user name
     * @return account object associated with user with given user name
     */
    Account findAccountByUsername(String username);

}
