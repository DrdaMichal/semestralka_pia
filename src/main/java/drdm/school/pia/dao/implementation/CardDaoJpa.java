package drdm.school.pia.dao.implementation;

import drdm.school.pia.dao.CardDao;
import drdm.school.pia.domain.entities.Card;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * JPA implementation of data access object for Card entity
 * @author Michal Drda
 */
@Repository
public class CardDaoJpa extends GenericDaoJpa<Card, Long> implements CardDao {

    /**
     * Constructor
     */
    public CardDaoJpa() {
        super(Card.class);
    }

    /**
     * {@inheritDoc}
     * This is an implementation of findByCardnumber method in CardDao interface
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
