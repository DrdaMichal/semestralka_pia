package drdm.school.pia.dao;

import drdm.school.pia.domain.entities.Card;

public interface CardDao extends GenericDao<Card, Long> {

    /**
     * Find a card by it's number
     * @param cardnumber provided cardnumber parameter
     * @return card object with given number
     */
    Card findByCardnumber(String cardnumber);

}
