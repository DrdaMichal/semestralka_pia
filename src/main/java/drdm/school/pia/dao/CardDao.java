package drdm.school.pia.dao;

import drdm.school.pia.domain.entities.Card;

/**
 * DAO interface for the Card entity
 * @author Michal Drda
 */
public interface CardDao extends GenericDao<Card, Long> {

    /**
     * Find a card by it's number
     * @param cardnumber provided card number parameter
     * @return card object with given number or null
     */
    Card findByCardnumber(String cardnumber);

}
