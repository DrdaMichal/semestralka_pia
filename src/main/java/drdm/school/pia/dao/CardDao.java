package drdm.school.pia.dao;

import drdm.school.pia.domain.Card;

public interface CardDao extends GenericDao<Card, Long> {

    /**
     * Find a card by it's user
     * @param username username related to the card to find
     * @return round card object
     */
    Card findByUsername(String username);

    /**
     * Find a card by it's number
     * @param cardnumber provided cardnumber parameter
     * @return card object with given number
     */
    Card findByCardnumber(String cardnumber);

    /**
     * Merge card with the existing one
     * @param card provided card object
     * @return merged card
     */
    Card merge(Card card);
}
