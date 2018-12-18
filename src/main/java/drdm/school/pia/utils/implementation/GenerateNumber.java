package drdm.school.pia.utils.implementation;

import drdm.school.pia.utils.LongGenerator;
import drdm.school.pia.web.servlet.spring.Login;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class used for generating numbers.
 * @author Michal Drda
 */
@Component
public class GenerateNumber implements LongGenerator {

    final static Logger logger = Logger.getLogger(Login.class);

    public GenerateNumber() {

    }

    /**
     * Generates a random number of positive value
     * @param numberLength number of digits
     * @return positive number of desired length
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

        logger.info("Generated number: [" + randomNumber + "]");

        return randomNumber;

    }

    /**
     * Generates a bank account from number count before "/" and bankcode
     * @param numberlength count of digits
     * @param bankcode 4digit bank code
     * @return bank account generated number
     */
    public String generateBankAccount(int numberlength, String bankcode) {
        return (generate(numberlength) + "/" + bankcode);
    }

}
