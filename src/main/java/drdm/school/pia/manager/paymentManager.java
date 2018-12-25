package drdm.school.pia.manager;

import drdm.school.pia.domain.Payment;
import drdm.school.pia.domain.PaymentValidationException;
import drdm.school.pia.domain.User;

import java.util.ArrayList;

public interface PaymentManager {

    void createPayment(Payment newPayment, String user) throws PaymentValidationException;

    ArrayList<String> loadPaymentTemplate(String username);
}
