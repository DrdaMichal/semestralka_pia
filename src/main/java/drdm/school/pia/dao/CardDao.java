package drdm.school.pia.dao;

import drdm.school.pia.domain.Card;

public interface CardDao extends GenericDao<Card, Long> {

    /**
     * Find a card by it's user
     * @param username
     * @return
     */
    Card findByUsername(String username);

    /**
     * Find a card by it's number
     * @param cardnumber
     * @return card with given number
     */
    Card findByCardnumber(String cardnumber);

}
