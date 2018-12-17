package drdm.school.pia.utils;

import java.util.regex.Pattern;

public interface Validator {

    Boolean isValid(String stringToValidate, String regex);
}
