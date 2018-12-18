package drdm.school.pia.utils.implementation;

import drdm.school.pia.utils.Validator;
import drdm.school.pia.web.servlet.spring.Login;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringValidator implements Validator {

    private static Pattern pattern;
    private static Matcher matcher;
    final static Logger logger = Logger.getLogger(Login.class);

    /**
     * Default constructor.
     */
    public StringValidator() {

    }

    /**
     * Validates stringToValidate with regex provided.
     * @param stringToValidate
     * @param regex
     * @return
     */
    @Override
        public Boolean isValid(String stringToValidate, String regex) {
        logger.info("Validate[" + stringToValidate + "," + regex + "]");

        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(stringToValidate);

        return matcher.matches();
    }
}
