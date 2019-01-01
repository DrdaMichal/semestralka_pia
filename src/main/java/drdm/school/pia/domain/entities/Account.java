package drdm.school.pia.domain.entities;

import drdm.school.pia.domain.BaseObject;
import drdm.school.pia.domain.IEntity;

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
    private double balance;
    private String number;
    private String bank;
    private Set<Card> cards = new LinkedHashSet<>();


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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @OneToMany(targetEntity = Card.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id) &&
                Objects.equals(balance, account.balance) &&
                Objects.equals(number, account.number) &&
                Objects.equals(bank, account.bank) &&
                Objects.equals(cards, account.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, number, bank, cards);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", number='" + number + '\'' +
                ", bank='" + bank + '\'' +
                ", cards=" + cards +
                '}';
    }
}
