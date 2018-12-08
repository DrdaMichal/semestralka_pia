package drdm.school.pia.domain;

/**
 * Exception thrown when User object fails internal state validation.
 * @author Michal Drda
 */
public class UserValidationException extends Exception {

    public UserValidationException(String message) {
        super(message);
    }

}
