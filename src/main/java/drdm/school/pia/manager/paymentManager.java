package drdm.school.pia.manager;

import drdm.school.pia.domain.entities.Payment;
import drdm.school.pia.domain.exceptions.PaymentValidationException;
import drdm.school.pia.domain.modules.Transaction;

import java.util.ArrayList;
import java.util.List;

public interface PaymentManager {

    void createPayment(Payment newPayment, String user) throws PaymentValidationException;

    ArrayList<String> loadPaymentTemplate(String username);

    Payment loadPaymentByTemplate(String username, String template);

    ArrayList<Transaction> findTransactionsForUsername(String username);
}
