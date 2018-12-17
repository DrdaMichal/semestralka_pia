package drdm.school.pia.utils;

import drdm.school.pia.web.servlet.spring.Login;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateString implements StringGenerator {


    private final static Logger logger = Logger.getLogger(Login.class);

    public GenerateString() {

    }

    @Override
    public String generate(int length) {
        String variants = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String generatedString = "";
        Random r = new Random();
        while (generatedString.length() < length) {
            generatedString = generatedString + variants.charAt(r.nextInt((variants.length() - 1) - 0));
        }
        logger.debug("Generated string [" + generatedString + "]");
        return generatedString;
    }

}