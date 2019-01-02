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
 *
 * @author Michal Drda
 */
@Entity
@Table(name = "drdam_payment")
public class Payment extends BaseObject implements IEntity<Long>, Serializable {

    private String selectedTemplate;
    private String recipientPreAccountNumber;
    private String recipientAccount;
    private String recipientBankCode;
    private String vs;
    private String cs;
    private String ss;
    private String recipientMessage;
    private String senderMessage;
    private BigDecimal amount;
    private String currency;
    private Date transactionDate;
    private String template;
    private String senderPreAccountNumber;
    private String senderAccount;
    private String senderBankCode;
    /**
     * A generated ID of the entity
     */
    private Long id;

    /**
     * Default constructor
     */
    private Date created;

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

    @Transient
    public String getSelectedTemplate() {
        return selectedTemplate;
    }

    public void setSelectedTemplate(String selectedTemplate) {
        this.selectedTemplate = selectedTemplate;
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

    public String getRecipientPreAccountNumber() {
        return recipientPreAccountNumber;
    }

    public void setRecipientPreAccountNumber(String recipientPreAccountNumber) {
        this.recipientPreAccountNumber = recipientPreAccountNumber;
    }

    public String getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(String recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public String getRecipientBankCode() {
        return recipientBankCode;
    }

    public void setRecipientBankCode(String recipientBankCode) {
        this.recipientBankCode = recipientBankCode;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(String senderMessage) {
        this.senderMessage = senderMessage;
    }

    public String getSenderPreAccountNumber() {
        return senderPreAccountNumber;
    }

    public void setSenderPreAccountNumber(String senderPreAccountNumber) {
        this.senderPreAccountNumber = senderPreAccountNumber;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getSenderBankCode() {
        return senderBankCode;
    }

    public void setSenderBankCode(String senderBankCode) {
        this.senderBankCode = senderBankCode;
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


    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Date getCreated() {
        return created;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(selectedTemplate, recipientPreAccountNumber, recipientAccount, recipientBankCode, vs, cs, ss, recipientMessage, senderMessage, amount, currency, transactionDate, template, senderPreAccountNumber, senderAccount, senderBankCode, id, created);
    }

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
