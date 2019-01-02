package drdm.school.pia.manager;

import drdm.school.pia.domain.entities.Account;
import drdm.school.pia.domain.entities.User;

/**
 * Card manager interface - used for managing cards
 * @author Michal Drda
 */
public interface CardManager {

    /**
     * Creates a card for user
     * @param user provided user to use the card (through his account)
     * @param account provided account object to link the card with
     */
    void createCard(User user, Account account);


}
