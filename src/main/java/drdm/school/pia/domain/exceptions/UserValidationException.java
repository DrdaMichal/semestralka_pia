package drdm.school.pia.domain.exceptions;

/**
 * Exception thrown when User object fails internal state validation.
 * @author Michal Drda
 */
public class UserValidationException extends Exception {

    /**
     * Constructor
     * @param message provided message to show with the exception
     */
    public UserValidationException(String message) {
        super(message);
    }

}
