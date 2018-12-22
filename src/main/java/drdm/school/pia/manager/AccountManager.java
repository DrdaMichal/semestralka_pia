package drdm.school.pia.manager;

import drdm.school.pia.domain.Account;
import drdm.school.pia.domain.Card;
import drdm.school.pia.domain.User;

import java.util.Set;

public interface AccountManager {

    /**
     * Creates a card for user
     * @param user user to own the card created
     */
    void createAccount(User user);

}
