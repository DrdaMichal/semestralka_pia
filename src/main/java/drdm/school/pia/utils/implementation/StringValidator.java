package drdm.school.pia.utils.implementation;

import drdm.school.pia.utils.Validator;
import drdm.school.pia.web.servlet.spring.Login;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @inheritDoc
 * Implementation of Validator interface, used for validation of strings by patterns
 * @author Michal Drda
 */
@Component
public class StringValidator implements Validator {

    /**
     * Pattern initialization
     */
    private static Pattern pattern;
    /**
     * Matcher initialization
     */
    private static Matcher matcher;
    /**
     * Logger initialization used for logging
     */
    final static Logger logger = Logger.getLogger(StringValidator.class);

    /**
     * Default constructor.
     */
    public StringValidator() {

    }

    /**
     * @inheritDoc
     * Validates stringToValidate against regex provided.
     */
    @Override
        public Boolean isValid(String stringToValidate, String regex) {
        logger.debug("Validate[" + stringToValidate + "," + regex + "]");

        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(stringToValidate);

        return matcher.matches();
    }
}
