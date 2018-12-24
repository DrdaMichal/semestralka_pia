package drdm.school.pia.manager;

import drdm.school.pia.domain.Account;
import drdm.school.pia.domain.Card;
import drdm.school.pia.domain.PaymentValidationException;
import drdm.school.pia.domain.User;

import java.util.Set;

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
    void updateBallance(Account account, Long valueOfChange) throws PaymentValidationException;

}
