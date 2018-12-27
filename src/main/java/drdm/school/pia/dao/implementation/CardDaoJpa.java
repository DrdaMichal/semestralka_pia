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
     * Find a card by it's user - implementation
     * @param username
     * @return
     */
    @Override
    public Card findByUsername(String username) {
        TypedQuery<Card> q = entityManager.createQuery("SELECT c FROM Card c WHERE c.user = :username_id", Card.class);
        q.setParameter("username_id", username);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
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

    @Override
    public Card merge(Card card) {
        entityManager.merge(card);
        return card;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

}
