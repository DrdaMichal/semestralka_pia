package drdm.school.pia.dto.implementation;

import drdm.school.pia.dto.TransferObject;

import java.util.Objects;

public class Transaction implements TransferObject {

    private String id;
    private String date;
    private String direction;
    private String account;
    private String amount;
    private String vs;
    private String cs;
    private String ss;
    private String yourMessage;
    private String theirMessage;

    public Transaction() {}

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

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVs() {
        return vs;
    }

    public void setVs(String vs) {
        this.vs = vs;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getYourMessage() {
        return yourMessage;
    }

    public void setYourMessage(String yourMessage) {
        this.yourMessage = yourMessage;
    }

    public String getTheirMessage() {
        return theirMessage;
    }

    public void setTheirMessage(String theirMessage) {
        this.theirMessage = theirMessage;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(id, direction, account, amount, vs, cs, ss, yourMessage, theirMessage);
    }

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
