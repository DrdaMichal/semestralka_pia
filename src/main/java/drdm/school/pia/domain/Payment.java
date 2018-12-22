package drdm.school.pia.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity representing a payment
 * @author Michal Drda
 */
@Entity
@Table(name = "drdam_payment")
public class Payment extends BaseObject implements IEntity<Long>, Serializable {

    private String selectedTemplate;
    private String sendTo;
    private String bankCode;
    private String vs;
    private String cs;
    private String ss;
    private String recipientMessage;
    private String myMessage;
    private String amount;
    private String currency;
    private String transactionDate;
    private String template;
    private Account account;

    private User user;

    /**
     * A generated ID of the entity
     */
    private Long id;

    /**
     * Default constructor
     */
    public Payment() {

    }

    /**
     * Main constructor.
     * @param sendTo
     * @param bankCode
     * @param vs
     * @param cs
     * @param ss
     * @param recipientMessage
     * @param myMessage
     * @param amount
     * @param currency
     * @param transactionDate
     */
    public Payment(String selectedTemplate, String sendTo, String bankCode, String vs, String cs, String ss, String recipientMessage, String myMessage, String amount, String currency, String transactionDate, String  template) {
        this.selectedTemplate = selectedTemplate;
        this.sendTo = sendTo;
        this.bankCode = bankCode;
        this.bankCode = bankCode;
        this.vs = vs;
        this.cs = cs;
        this.ss = ss;
        this.recipientMessage = recipientMessage;
        this.myMessage = myMessage;
        this.amount = amount;
        this.currency = currency;
        this.transactionDate = transactionDate;
        this.template = template;
    }

    @Override
    @Transient
    public Long getPK() {
        return getId();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

/*    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Transient
    public String getSelectedTemplate() {
        return selectedTemplate;
    }

    public void setSelectedTemplate(String selectedTemplate) {
        this.selectedTemplate = selectedTemplate;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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

    public String getRecipientMessage() {
        return recipientMessage;
    }

    public void setRecipientMessage(String recipientMessage) {
        this.recipientMessage = recipientMessage;
    }

    public String getMyMessage() {
        return myMessage;
    }

    public void setMyMessage(String myMessage) {
        this.myMessage = myMessage;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return sendTo.equals(payment.sendTo) &&
                bankCode.equals(payment.bankCode) &&
                Objects.equals(vs, payment.vs) &&
                Objects.equals(cs, payment.cs) &&
                Objects.equals(ss, payment.ss) &&
                Objects.equals(recipientMessage, payment.recipientMessage) &&
                Objects.equals(myMessage, payment.myMessage) &&
                amount.equals(payment.amount) &&
                Objects.equals(currency, payment.currency) &&
                transactionDate.equals(payment.transactionDate) &&
                Objects.equals(template, payment.template) &&
                id.equals(payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sendTo, bankCode, vs, cs, ss, recipientMessage, myMessage, amount, currency, transactionDate, template, id);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "selectedTemplate='" + selectedTemplate + '\'' +
                ", sendTo='" + sendTo + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", vs='" + vs + '\'' +
                ", cs='" + cs + '\'' +
                ", ss='" + ss + '\'' +
                ", recipientMessage='" + recipientMessage + '\'' +
                ", myMessage='" + myMessage + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", template='" + template + '\'' +
                ", id=" + id +
                '}';
    }

}
