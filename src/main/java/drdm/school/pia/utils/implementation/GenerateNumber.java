package drdm.school.pia.utils.implementation;

import drdm.school.pia.utils.LongGenerator;
import drdm.school.pia.web.servlet.spring.Login;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * {@inheritDoc}
 * Class used for generating numbers.
 * @author Michal Drda
 */
@Component
public class GenerateNumber implements LongGenerator {

    /**
     * Logger used for the GenerateNumber class
     */
    final static Logger logger = Logger.getLogger(GenerateNumber.class);

    /**
     * Constructor
     */
    public GenerateNumber() {

    }

    /**
     * {@inheritDoc}
     * Generates a random number of positive value
     */
    public long generate(int numberLength) {
        Random rnd = new Random();
        String temp = "";

        long lowerBound = (long) Math.pow(10, (numberLength-1));
        long addition = (long) (Math.pow(10, (numberLength-1)) * 9);

        long randomNumber = (lowerBound + ThreadLocalRandom.current().nextLong(addition));

        if(randomNumber < 0) {
            randomNumber = randomNumber * (-1);
        }

        if(String.valueOf(randomNumber).length() != numberLength) {
            generate(numberLength);
        }

        logger.debug("Generated number: [" + randomNumber + "]");

        return randomNumber;

    }

}
