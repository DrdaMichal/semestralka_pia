package drdm.school.pia.domain.entities;

import drdm.school.pia.domain.BaseObject;
import drdm.school.pia.domain.IEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Account entity, used for storing account information for User
 * @author Michal Drda
 */
@Entity
@Table(name="drdam_account")
public class Account extends BaseObject implements IEntity<Long>, Serializable {

    /**
     * A generated ID of the entity
     */
    private Long id;
    /**
     * Balance of the account
     */
    private BigDecimal balance;
    /**
     * Account number
     */
    private String number;
    /**
     * Bank code of the account
     */
    private String bank;
    /**
     * Cards assigned to the account
     */
    private Set<Card> cards = new LinkedHashSet<>();

    /**
     * Default constructor
     */
    public Account() {

    }

    /**
     * @inheritDoc
     * Primary key getter implementation
     */
    @Transient
    @Override
    public Long getPK() { return getId(); }

    /**
     * Primary key of the account getter
     * @return account primary key (id)
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
     * Getter for an account number
     * @return account number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Setter for an account number
     * @param number account number provided
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Getter for a bank code
     * @return bank code
     */
    public String getBank() {
        return bank;
    }

    /**
     * Setter for a bank code
     * @param bank provided bank code to be set to the account
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * Getter for a balance on the account
     * @return balance on the account
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Setter for a balance on the account
     * @param balance provided balance to be set to account
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    /**
     * OneToMany association between account and it's cards.
     * Multiple cards may be attached to one account, Account may have multiple cards
     * Getter for Cards list linked to account
     * @return cards list linked to account
     */
    @OneToMany(targetEntity = Card.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    public Set<Card> getCards() {
        return cards;
    }

    /**
     * Setter to set cards assigned to account
     * @param cards set of cards provided
     */
    public void setCards(Set<Card> cards) {
        this.cards = cards;
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
        Account account = (Account) o;
        return id.equals(account.id) &&
                Objects.equals(balance, account.balance) &&
                Objects.equals(number, account.number) &&
                Objects.equals(bank, account.bank) &&
                Objects.equals(cards, account.cards);
    }

    /**
     * Generated hash code method
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, balance, number, bank);
    }

    /**
     * Generated toString method
     * @return account printed to string
     */
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", number='" + number + '\'' +
                ", bank='" + bank +
                '}';
    }

}
