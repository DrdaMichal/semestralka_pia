package drdm.school.pia.domain.entities;

import drdm.school.pia.domain.BaseObject;
import drdm.school.pia.domain.IEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Card entity, used for storing card information for Account linked to User
 * @author Michal Drda
 */
@Entity
@Table(name = "drdam_card")
public class Card extends BaseObject implements IEntity<Long>, Serializable {

    /**
     * A generated ID of the entity
     */
    private Long id;
    /**
     * Number of card
     */
    private String cardNumber;
    /**
     * CVC code of the card
     */
    private String cvc;
    /**
     * Expiration of the card
     */
    private String cardExpiration;
    /**
     * Pin of the card
     */
    private String pin;
    /**
     * Account of the card
     */
    private Account account;

    /**
     * Default constructor
     */
    public Card() {

    }

    /**
     * Constructor used to generate the card
     * @param cardNumber provided card number
     * @param cvc provided cvc code
     * @param cardExpiration provided expiration of the card
     * @param pin provided pin code
     */
    public Card(String cardNumber, String cvc, String cardExpiration, String pin) {
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.cardExpiration = cardExpiration;
        this.pin = pin;
    }

    /**
     * @inheritDoc
     * Primary key getter implementation
     */
    @Transient
    @Override
    public Long getPK() {
        return getId();
    }

    /**
     * Primary key of the card getter
     * @return card primary key (id)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Setter for primary key
     * @param id provided id number
     */
    public void setId(Long id) { this.id = id; }

    /**
     * ManyToOne association between Card and it's Account.
     * Account may be attached to multiple cards, Card may have one account
     * Getter for account object linked to card
     * @return account instance linked to card
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    public Account getAccount() {
        return account;
    }

    /**
     * Setter for the account associated with the card
     * @param account provided card object
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Getter for a card number
     * @return card number provided
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Setter for a card number
     * @param cardNumber provided card number
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Getter for a cvc code of the card
     * @return cvc code assigned to the card
     */
    public String getCvc() {
        return cvc;
    }

    /**
     * Setter for a cvc code
     * @param cvc provided cvc code
     */
    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    /**
     * Getter for a pin code
     * @return provided pin code
     */
    public String getPin() {
        return pin;
    }

    /**
     * Setter for a pin code
     * @param pin provided pin code
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    /**
     * Getter for a card expiration
     * @return expiration of the card
     */
    public String getCardExpiration() {
        return cardExpiration;
    }

    /**
     * Setter for a card expiration
     * @param cardExpiration provided card expiration
     */
    public void setCardExpiration(String cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    /**
     * Generated equals method
     * @param o provided object to be compared
     * @return true if objects are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) &&
                Objects.equals(cardNumber, card.cardNumber) &&
                Objects.equals(cvc, card.cvc) &&
                Objects.equals(cardExpiration, card.cardExpiration) &&
                Objects.equals(pin, card.pin) &&
                Objects.equals(account, card.account);
    }

    /**
     * Generated hash code method
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, cvc, cardExpiration, pin);
    }

    /**
     * Generated toString method
     * @return Card printed to string
     */
    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvc='" + cvc + '\'' +
                ", cardExpiration='" + cardExpiration + '\'' +
                ", pin='" + pin +
                '}';
    }

}
