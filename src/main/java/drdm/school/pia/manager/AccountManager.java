package drdm.school.pia.manager;

import drdm.school.pia.domain.entities.Account;
import drdm.school.pia.domain.exceptions.PaymentValidationException;
import drdm.school.pia.domain.entities.User;

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
    void updateBallance(Account account, double valueOfChange) throws PaymentValidationException;

    Account findAccountByUsername(String username);

}
