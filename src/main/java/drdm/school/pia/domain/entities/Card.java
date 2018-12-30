package drdm.school.pia.domain.entities;

import drdm.school.pia.domain.BaseObject;
import drdm.school.pia.domain.IEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "drdam_card")
public class Card extends BaseObject implements IEntity<Long>, Serializable {

    private Long id;
    private String cardNumber;
    private String cvc;
    private String cardExpiration;
    private String pin;
    private Account account;

    private User user;

    public Card() {

    }

    public Card(String cardNumber, String cvc, String cardExpiration, String pin) {
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.cardExpiration = cardExpiration;
        this.pin = pin;
    }

    @Transient
    @Override
    public Long getPK() {
        return getId();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

/*    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public User getUser() {
        return user;
    }*/

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCardExpiration() {
        return cardExpiration;
    }

    public void setCardExpiration(String cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card1 = (Card) o;
        return id.equals(card1.id) &&
                cardNumber.equals(card1.cardNumber) &&
                cvc.equals(card1.cvc) &&
                cardExpiration.equals(card1.cardExpiration) &&
                Objects.equals(user, card1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, cvc, cardExpiration, user);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvc='" + cvc + '\'' +
                ", cardExpiration='" + cardExpiration + '\'' +
                ", pin='" + pin + '\'' +
                '}';
    }

}
