package drdm.school.pia.manager;

import drdm.school.pia.domain.entities.Account;
import drdm.school.pia.domain.exceptions.PaymentValidationException;
import drdm.school.pia.domain.entities.User;

/**
 * Account manager
 * @author Michal Drda
 */
public interface AccountManager {

    /**
     * Creates a card for user
     * @param user user to own the card created
     */
    void createAccount(User user);

    /**
     * Method used to manipulate with balance of the account
     * @param account account which balance to be adjusted
     * @param valueOfChange valueOfChange provided as an addition or a substraction from the current account balance
     */
    void updateBalance(Account account, double valueOfChange) throws PaymentValidationException;

    /**
     * Finds an account by given user name
     * @param username user name provided
     * @return account object associated with user with given user name
     */
    Account findAccountByUsername(String username);

}
