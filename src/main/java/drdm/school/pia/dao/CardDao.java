package drdm.school.pia.dao;

import drdm.school.pia.domain.Card;

public interface CardDao extends GenericDao<Card, Long> {

    /**
     * Find a card by it's user
     * @param username
     * @return
     */
    Card findByUser(String username);

    /**
     * Find a card by it's number
     * @param cardNumber
     * @return card with given number
     */
    Card findByCardNumber(String cardNumber);

}
