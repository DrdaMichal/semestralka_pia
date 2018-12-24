package drdm.school.pia.manager;

import drdm.school.pia.domain.Payment;
import drdm.school.pia.domain.PaymentValidationException;
import drdm.school.pia.domain.User;

public interface PaymentManager {

    void createPayment(Payment newPayment, String user) throws PaymentValidationException;
}
