package drdm.school.pia.utils;

/**
 * String validation interface interface
 * @author Michal Drda
 */
public interface Validator {

    /**
     * Used for validation of the string
     * @param stringToValidate provided string to validate
     * @param regex pattern to be used for validation
     * @return true in case that string matches the pattern or false if not
     */
    Boolean isValid(String stringToValidate, String regex);
}
