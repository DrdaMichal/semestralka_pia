package drdm.school.pia.utils.implementation;

import drdm.school.pia.utils.StringGenerator;
import drdm.school.pia.web.servlet.spring.Login;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @inheritDoc
 * Implementation of the StringGenerator
 * @author Michal Drda
 */
@Component
public class GenerateString implements StringGenerator {

    /**
     * Logger initialization for logging of useful info
     */
    private final static Logger logger = Logger.getLogger(Login.class);
    /**
     * Pattern used for String generator (source vlaues to be used)
     */
    @Value("${generator.pattern.string}")
    String patternForGeneration;

    /**
     * Default constructor
     */
    public GenerateString() {

    }

    /**
     * @inheritDoc
     * Used for generation of the string
     */
    @Override
    public String generate(int length) {
        String generatedString = "";
        Random r = new Random();
        while (generatedString.length() < length) {
            generatedString = generatedString + patternForGeneration.charAt(r.nextInt((patternForGeneration.length() - 1) - 0));
        }
        logger.debug("Generated string [" + generatedString + "]");
        return generatedString;
    }

}