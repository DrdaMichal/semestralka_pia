package drdm.school.pia.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "drdam_cards")
public class Card extends BaseObject implements IEntity<Long>, Serializable {

    private Long id;
    private String cardNo;
    private String cvc;
    private String cardExpiration;
    private String pin;

    private User user;

    public Card() {

    }

    public Card(String cardNo, String cvc, String cardExpiration, String pin) {
        this.cardNo = cardNo;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="username_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCardNumber() {
        return cardNo;
    }

    public void setCardNumber(String cardNo) {
        this.cardNo = cardNo;
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
                cardNo.equals(card1.cardNo) &&
                cvc.equals(card1.cvc) &&
                cardExpiration.equals(card1.cardExpiration) &&
                Objects.equals(user, card1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNo, cvc, cardExpiration, user);
    }


}
