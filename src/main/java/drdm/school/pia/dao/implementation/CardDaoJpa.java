package drdm.school.pia.dao.implementation;

import drdm.school.pia.dao.CardDao;
import drdm.school.pia.domain.entities.Card;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class CardDaoJpa extends GenericDaoJpa<Card, Long> implements CardDao {

    /**
     * Constructor
     */
    public CardDaoJpa() {
        super(Card.class);
    }

    /**
     * Find a card by it's number - implementation
     * @param cardnumber
     * @return
     */
    @Override
    public Card findByCardnumber(String cardnumber) {
        TypedQuery<Card> q = entityManager.createQuery("SELECT c FROM Card c WHERE c.cardNumber = :cardnumber", Card.class);
        q.setParameter("cardnumber", cardnumber);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
