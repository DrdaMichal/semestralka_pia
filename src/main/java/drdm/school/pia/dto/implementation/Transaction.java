package drdm.school.pia.dto.implementation;

import drdm.school.pia.dto.TransferObject;

import java.util.Objects;

/**
 * DTO implementation of the Transaction - Payment to be shown in the payments history, but with specific values for each party of the transaction
 * @author Michal Drda
 */
public class Transaction implements TransferObject {

    /**
     * id of the transaction
     */
    private String id;
    /**
     * Date of the transaction
     */
    private String date;
    /**
     * Direction of the transaction
     */
    private String direction;
    /**
     * Account of the sender (in case of inbound direction)
     * Account of the recipient (in case of outbound direction)
     * Including prefix & bank Code
     */
    private String account;
    /**
     * Amount of the transaction
     */
    private String amount;
    /**
     * Variable symbol
     */
    private String vs;
    /**
     * Constant symbol
     */
    private String cs;
    /**
     * Specific symbol
     */
    private String ss;
    /**
     * User's message for the transaction
     */
    private String yourMessage;
    /**
     * Other user's message for the transaction
     */
    private String theirMessage;

    /**
     * Default constructor
     */
    public Transaction() {}

    /**
     * Constructor to build a transaction
     * @param id id of the transaction
     * @param date date of the transaction
     * @param direction direction of the transaction
     * @param account other user's account (including bank code and prefix) of the transaction
     * @param amount amount of the transaction
     * @param vs variable symbol of the transaction
     * @param cs constant symbol of the transaction
     * @param ss specific symbol of the transaction
     * @param yourMessage user's message of the transaction
     * @param theirMessage other user's message of the transaction
     */
    public Transaction(String id, String date, String direction, String account, String amount, String vs, String cs, String ss, String yourMessage, String theirMessage) {
        this.id = id;
        this.date = date;
        this.direction = direction;
        this.account = account;
        this.amount = amount;
        this.vs = vs;
        this.cs = cs;
        this.ss = ss;
        this.yourMessage = yourMessage;
        this.theirMessage = theirMessage;
    }

    /**
     * Getter to get the id of the transaction
     * @return id of the dto
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Setter of the id
     * @param id provided id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for a date of the transaction
     * @return date of the transaction
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter for a date of the transaction
     * @param date provided date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter for a direction of the transaction (In/Out probably)
     * @return direction of the transaction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Setter for a direction of the transaction
     * @param direction provided direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Getter for an account of the other user
     * @return account of the other user
     */
    public String getAccount() {
        return account;
    }

    /**
     * Setter for an account of the other user
     * @param account provided account of the other user
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Getter of the amount of the transaction
     * @return amount of the transaction
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Setter of the amount of the transaction
     * @param amount provided amount of the transaction
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Getter of the variable symbol
     * @return variable symbol
     */
    public String getVs() {
        return vs;
    }

    /**
     * Setter of the variable symbol
     * @param vs provided variable symbol
     */
    public void setVs(String vs) {
        this.vs = vs;
    }

    /**
     * Getter of the constant symbol
     * @return constant symbol
     */
    public String getCs() {
        return cs;
    }

    /**
     * Setter of the constant symbol
     * @param cs provided constant symbol
     */
    public void setCs(String cs) {
        this.cs = cs;
    }

    /**
     * Getter of the specific symbol
     * @return specific symbol
     */
    public String getSs() {
        return ss;
    }

    /**
     * Setter for a specific symbol
     * @param ss provided specific symbol
     */
    public void setSs(String ss) {
        this.ss = ss;
    }

    /**
     * Getter for a user's message
     * @return user's message
     */
    public String getYourMessage() {
        return yourMessage;
    }

    /**
     * Setter of the user's message
     * @param yourMessage provided user's message
     */
    public void setYourMessage(String yourMessage) {
        this.yourMessage = yourMessage;
    }

    /**
     * Getter for the other user's message
     * @return other user's message
     */
    public String getTheirMessage() {
        return theirMessage;
    }

    /**
     * Setter of the other user's message
     * @param theirMessage other user's message
     */
    public void setTheirMessage(String theirMessage) {
        this.theirMessage = theirMessage;
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
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(direction, that.direction) &&
                Objects.equals(account, that.account) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(vs, that.vs) &&
                Objects.equals(cs, that.cs) &&
                Objects.equals(ss, that.ss) &&
                Objects.equals(yourMessage, that.yourMessage) &&
                Objects.equals(theirMessage, that.theirMessage);
    }

    /**
     * Generated hash code method
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, direction, account, amount, vs, cs, ss, yourMessage, theirMessage);
    }

    /**
     * Generated toString method
     * @return Transaction printed to string
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", direction='" + direction + '\'' +
                ", account='" + account + '\'' +
                ", amount='" + amount + '\'' +
                ", vs='" + vs + '\'' +
                ", cs='" + cs + '\'' +
                ", ss='" + ss + '\'' +
                ", yourMessage='" + yourMessage + '\'' +
                ", theirMessage='" + theirMessage + '\'' +
                '}';
    }

}
