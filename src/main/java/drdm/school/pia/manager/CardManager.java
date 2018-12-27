package drdm.school.pia.manager;

import drdm.school.pia.domain.entities.Account;
import drdm.school.pia.domain.entities.User;

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
