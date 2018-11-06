package drdm.school.pia.utils;

import java.util.Random;

public class generateString {

    public generateString()  {

    }

    public static String getRandomString(int length) {
        return generateRandomString(length);
    }

    private static String generateRandomString(int length) {
        String variants = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String generatedString = "";
        Random r =  new Random();
        while (generatedString.length() < length) {
            generatedString =  generatedString + variants.charAt(r.nextInt((variants.length()-1)-0));
        }
        System.out.println("Username is: " + generatedString);
        return generatedString;
    }

    //TODO checking DB for uniqueness

}
