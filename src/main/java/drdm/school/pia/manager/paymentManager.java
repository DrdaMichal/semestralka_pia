package drdm.school.pia.manager;

import drdm.school.pia.domain.entities.Payment;
import drdm.school.pia.domain.exceptions.PaymentValidationException;
import drdm.school.pia.dto.implementation.Transaction;

import java.util.ArrayList;

/**
 * Payment manager interface - used for managing payments
 * @author Michal Drda
 */
public interface PaymentManager {

    /**
     * Creates a new payment, processes the payment through the system, transfers the money, and fills the database
     * @param newPayment new Payment object provided
     * @param user user name provided
     * @throws PaymentValidationException in case of payment validation error
     */
    void createPayment(Payment newPayment, String user) throws PaymentValidationException;

    /**
     * Used for loading of the template list, finding templates for a provided user
     * @param username user name provided
     * @return list of payments connected with provided username parameter, that are templates
     */
    ArrayList<String> loadPaymentTemplate(String username);

    /**
     * Loads a payment by it's template name for selected user
     * @param username provided user name
     * @param template provided template name
     * @return template found with given parameters
     */
    Payment loadPaymentByTemplate(String username, String template);

    /**
     * Loads all payments connected to the user
     * @param username provided user name
     * @return list of the payments, that are connected with provided user name
     */
    ArrayList<Transaction> findTransactionsForUsername(String username);
}
