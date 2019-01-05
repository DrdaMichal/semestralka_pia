package drdm.school.pia.domain.entities;

import drdm.school.pia.domain.BaseObject;
import drdm.school.pia.domain.IEntity;
import drdm.school.pia.domain.exceptions.PaymentValidationException;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Entity representing a payment
 * @author Michal Drda
 */
@Entity
@Table(name = "drdam_payment")
public class Payment extends BaseObject implements IEntity<Long>, Serializable {

    /**
     * Template that was selected to be loaded
     */
    private String selectedTemplate;
    /**
     * Pre account number for recipient
     */
    private String recipientPreAccountNumber;
    /**
     * Account number for recipient
     */
    private String recipientAccount;
    /**
     * Bank code for recipient
     */
    private String recipientBankCode;
    /**
     * Variable symbol
     */
    private String vs;
    /**
     * Constnat symbol
     */
    private String cs;
    /**
     * Specific symbol
     */
    private String ss;
    /**
     * Message for recipient
     */
    private String recipientMessage;
    /**
     * Message for sender
     */
    private String senderMessage;
    /**
     * Amount to be transfered in the payment
     */
    private BigDecimal amount;
    /**
     * Currency of the transfer
     */
    private String currency;
    /**
     * Date when the transaction should be executed (but not really - it's done instantly in fact)
     */
    private Date transactionDate;
    /**
     * Template name to store this payment as a template
     */
    private String template;
    /**
     * Pre account number for sender
     */
    private String senderPreAccountNumber;
    /**
     * Account number for sender
     */
    private String senderAccount;
    /**
     * Bank code for sender
     */
    private String senderBankCode;
    /**
     * A generated ID of the entity
     */
    private Long id;
    /**
     * Default constructor
     */
    private Date created;

    /**
     * Default constructor
     */
    public Payment() {

    }

    /**
     * Main constructor used for creating new Payment
     * @param selectedTemplate provided selected template to be used
     * @param recipientAccount provided recipient account number
     * @param recipientBankCode provided recipient bank code number
     * @param recPreAccountNumber provided recipient pre account number
     * @param senderAccount provided sender account number
     * @param senderBankCode provided sender bank code number
     * @param sndPreAccountNumber provided sender pre account number
     * @param vs provided variable symbol
     * @param cs provided constant symbol
     * @param ss provided specific symbol
     * @param recipientMessage provided receiver message attached to the payment
     * @param senderMessage provided sender message attached to the payment
     * @param amount provided money amount
     * @param currency provided currency code
     * @param transactionDate  provided in format dd-MM-yyyy
     * @param template provided template name
     */
    public Payment(String selectedTemplate, String recipientAccount, String recipientBankCode, String recPreAccountNumber, String senderAccount, String senderBankCode, String sndPreAccountNumber, String vs, String cs, String ss, String recipientMessage, String senderMessage, String amount, String currency, Date transactionDate, String  template) {
        this.selectedTemplate = selectedTemplate;
        this.recipientAccount = recipientAccount;
        this.recipientBankCode = recipientBankCode;
        this.recipientPreAccountNumber = recPreAccountNumber;
        this.senderAccount = senderAccount;
        this.senderBankCode = senderBankCode;
        this.senderPreAccountNumber = sndPreAccountNumber;
        this.vs = vs;
        this.cs = cs;
        this.ss = ss;
        this.recipientMessage = recipientMessage;
        this.senderMessage = senderMessage;
        this.amount = new BigDecimal(amount.replace(",", "."));
        this.currency = currency;
        this.transactionDate = transactionDate;
        this.template = template;
    }

    /**
     * {@inheritDoc}
     * Primary key getter implementation
     */
    @Override
    @Transient
    public Long getPK() {
        return getId();
    }

    /**
     * Primary key of the Payment, getter
     * @return id - primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Setter for an id
     * @param id id provided to be set
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for a template that was selected to be loaded
     * @return selected template to be loaded
     */
    @Transient
    public String getSelectedTemplate() {
        return selectedTemplate;
    }

    /**
     * Setter for a template to be selected and loaded
     * @param selectedTemplate provided template name
     */
    public void setSelectedTemplate(String selectedTemplate) {
        this.selectedTemplate = selectedTemplate;
    }

    /**
     * Getter for a variable symbol
     * @return variable symbol
     */
    public String getVs() {
        return vs;
    }

    /**
     * Setter for a variable symbol
     * @param vs variable symbol provided
     */
    public void setVs(String vs) {
        this.vs = vs;
    }

    /**
     * Getter for a constant symbol
     * @return constant symbol
     */
    public String getCs() {
        return cs;
    }

    /**
     * Getter for a prefix of account number of recipient
     * @return recipient account number prefix
     */
    public String getRecipientPreAccountNumber() {
        return recipientPreAccountNumber;
    }

    /**
     * Setter for a prefix of account number of recipient
     * @param recipientPreAccountNumber recipient account number prefix provided
     */
    public void setRecipientPreAccountNumber(String recipientPreAccountNumber) {
        this.recipientPreAccountNumber = recipientPreAccountNumber;
    }

    /**
     * Getter for an account number of the recipient
     * @return account of the recipient
     */
    public String getRecipientAccount() {
        return recipientAccount;
    }

    /**
     * Setter for a recipient account number
     * @param recipientAccount account number of a recipient provided
     */
    public void setRecipientAccount(String recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    /**
     * Getter to get the bank code of the recipient
     * @return bank code of the recipient
     */
    public String getRecipientBankCode() {
        return recipientBankCode;
    }

    /**
     * Setter of the bank code of the recipient account
     * @param recipientBankCode bank code of the recipient account
     */
    public void setRecipientBankCode(String recipientBankCode) {
        this.recipientBankCode = recipientBankCode;
    }

    /**
     * Getter for a sender message
     * @return sender message attached to the payment
     */
    public String getSenderMessage() {
        return senderMessage;
    }

    /**
     * Setter for a sender message
     * @param senderMessage provided sender message
     */
    public void setSenderMessage(String senderMessage) {
        this.senderMessage = senderMessage;
    }

    /**
     * Getter for a sender prefix of the account number
     * @return prefix of the account number of sender
     */
    public String getSenderPreAccountNumber() {
        return senderPreAccountNumber;
    }

    /**
     * Setter of the sender prefix of the account number
     * @param senderPreAccountNumber prefix of the account number of sender provided
     */
    public void setSenderPreAccountNumber(String senderPreAccountNumber) {
        this.senderPreAccountNumber = senderPreAccountNumber;
    }

    /**
     * Getter of the sender account number
     * @return account number of the sender
     */
    public String getSenderAccount() {
        return senderAccount;
    }

    /**
     * Setter of the account number of the sender
     * @param senderAccount sender account number provided
     */
    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    /**
     * Getter of the sender bank code
     * @return bank code of the sender account
     */
    public String getSenderBankCode() {
        return senderBankCode;
    }

    /**
     * Setter of the bank code for sender
     * @param senderBankCode sender bank code provided
     */
    public void setSenderBankCode(String senderBankCode) {
        this.senderBankCode = senderBankCode;
    }

    /**
     * Setter for a constant symbol
     * @param cs constant symbol provided
     */
    public void setCs(String cs) {
        this.cs = cs;
    }

    /**
     * Getter for a specific symbol
     * @return specific symbol of the payment
     */
    public String getSs() {
        return ss;
    }

    /**
     * Setter of the specific symbol of the payment
     * @param ss provided specific symbol of the payment
     */
    public void setSs(String ss) {
        this.ss = ss;
    }

    /**
     * Getter of the recipient message
     * @return recipient message
     */
    public String getRecipientMessage() {
        return recipientMessage;
    }

    /**
     * Setter of the recipient message
     * @param recipientMessage provided recipient message
     */
    public void setRecipientMessage(String recipientMessage) {
        this.recipientMessage = recipientMessage;
    }

    /**
     * Getter of the amount of the transaction
     * @return amount of the transaction
     */
    public BigDecimal getAmount() { return amount; }

    /**
     * Setter of the amount of the transaction
     * @param amount provided amount to be set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Getter of the currency of the transaction
     * @return currency of the transaction
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Setter of the currency
     * @param currency provided currency code
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Getter of the transaction date
     * @return transaction date - date when the payment is executed
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Setter of the transaction date
     * @param transactionDate provided date when the payment should be executed
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Getter for the template assigned to the transaction
     * @return template name under which the transaction could be loaded in future
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Setter of the template name to the transaction
     * @param template provided template name
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * Getter of the date when the payment was created
     * @return date of the payment creation
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Setter of the payment creation
     * @param created date provided when the payment was created (should be the timestamp of new instance of Payment creation)
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * Validates that payment instance is currently in a valid state.
     * @throws PaymentValidationException in case the instance is not in valid state.
     */
    public void validate() throws PaymentValidationException {
        // BigDecimal comparison a>b --> a.compareTo(b) > 0
        if(!(amount.compareTo(new BigDecimal(0)) > 0)) throw new PaymentValidationException("Amount can't be zero or less!");
        if(StringUtils.isBlank(recipientBankCode)) throw new PaymentValidationException("Bank code is a required field!");
        if(StringUtils.isBlank(recipientAccount)) throw new PaymentValidationException("Receiving account is a required field!");
        if(null == (transactionDate)) throw new PaymentValidationException("Transaction date is a required field!");
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
        Payment payment = (Payment) o;
        return Objects.equals(selectedTemplate, payment.selectedTemplate) &&
                Objects.equals(recipientPreAccountNumber, payment.recipientPreAccountNumber) &&
                Objects.equals(recipientAccount, payment.recipientAccount) &&
                Objects.equals(recipientBankCode, payment.recipientBankCode) &&
                Objects.equals(vs, payment.vs) &&
                Objects.equals(cs, payment.cs) &&
                Objects.equals(ss, payment.ss) &&
                Objects.equals(recipientMessage, payment.recipientMessage) &&
                Objects.equals(senderMessage, payment.senderMessage) &&
                Objects.equals(amount, payment.amount) &&
                Objects.equals(currency, payment.currency) &&
                Objects.equals(transactionDate, payment.transactionDate) &&
                Objects.equals(template, payment.template) &&
                Objects.equals(senderPreAccountNumber, payment.senderPreAccountNumber) &&
                Objects.equals(senderAccount, payment.senderAccount) &&
                Objects.equals(senderBankCode, payment.senderBankCode) &&
                Objects.equals(id, payment.id) &&
                Objects.equals(created, payment.created);
    }

    /**
     * Generated hash code method
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(selectedTemplate, recipientPreAccountNumber, recipientAccount, recipientBankCode, vs, cs, ss, recipientMessage, senderMessage, amount, currency, transactionDate, template, senderPreAccountNumber, senderAccount, senderBankCode, id, created);
    }

    /**
     * Generated toString method
     * @return Payment printed to string
     */
    @Override
    public String toString() {
        return "Payment{" +
                "selectedTemplate='" + selectedTemplate + '\'' +
                ", recipientPreAccountNumber='" + recipientPreAccountNumber + '\'' +
                ", recipientAccount='" + recipientAccount + '\'' +
                ", recipientBankCode='" + recipientBankCode + '\'' +
                ", vs='" + vs + '\'' +
                ", cs='" + cs + '\'' +
                ", ss='" + ss + '\'' +
                ", recipientMessage='" + recipientMessage + '\'' +
                ", senderMessage='" + senderMessage + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", transactionDate=" + transactionDate +
                ", template='" + template + '\'' +
                ", senderPreAccountNumber='" + senderPreAccountNumber + '\'' +
                ", senderAccount='" + senderAccount + '\'' +
                ", senderBankCode='" + senderBankCode + '\'' +
                ", id=" + id +
                ", created=" + created +
                '}';
    }
}
