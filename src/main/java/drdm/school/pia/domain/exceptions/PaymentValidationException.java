package drdm.school.pia.domain.exceptions;

/**
 * Exception thrown when Payment object fails internal state validation.
 * @author Michal Drda
 */
public class PaymentValidationException extends Exception {

    /**
     * Constructor
     * @param message provided message to show with the exception
     */
    public PaymentValidationException(String message) {
        super(message);
    }
}
