package drdm.school.pia.manager;

import drdm.school.pia.domain.Account;
import drdm.school.pia.domain.User;

/**
 * @author Michal Drda
 */
public interface CardManager {

    /**
     * Creates a card for user
     * @param user user to own the card created
     */
    void createCard(User user, Account account);


}
