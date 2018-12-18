package drdm.school.pia.dao.implementation;

import drdm.school.pia.dao.CardDao;
import drdm.school.pia.domain.Card;
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

    @Override
    public Card findByUser(String username) {
        TypedQuery<Card> q = entityManager.createQuery("SELECT c FROM Card c WHERE c.user = :username_id", Card.class);
        q.setParameter("username_id", username);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Card findByCardNumber(String cardNumber) {
        return null;
    }

}
