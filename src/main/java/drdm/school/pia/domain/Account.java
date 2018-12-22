package drdm.school.pia.domain;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Michal Drda
 */
@Entity
@Table(name="drdam_account")
public class Account extends BaseObject implements IEntity<Long>, Serializable {

    /**
     * A generated ID of the entity
     */
    private Long id;
    private Long balance;
    private String number;
    private String bank;
    private boolean blocked;
    private Set<Card> cards = new LinkedHashSet<>();
    private Set<Payment> payments = new LinkedHashSet<>();
    private User user;


    public Account() {

    }

    @Transient
    @Override
    public Long getPK() { return getId(); }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) { this.id = id; }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @OneToMany(targetEntity = Card.class, cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    @OneToMany(targetEntity = Payment.class, cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return blocked == account.blocked &&
                Objects.equals(id, account.id) &&
                Objects.equals(balance, account.balance) &&
                Objects.equals(number, account.number) &&
                Objects.equals(bank, account.bank) &&
                Objects.equals(cards, account.cards) &&
                Objects.equals(payments, account.payments) &&
                Objects.equals(user, account.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, number, bank, blocked, cards, payments, user);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", number='" + number + '\'' +
                ", bank code='" + bank + '\'' +
                ", blocked=" + blocked +
                '}';
    }

}
