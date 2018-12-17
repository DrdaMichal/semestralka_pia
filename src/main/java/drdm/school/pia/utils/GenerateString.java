package drdm.school.pia.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateString implements StringGenerator {

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
        System.out.println("Username is: " + generatedString);
        return generatedString;
    }

}