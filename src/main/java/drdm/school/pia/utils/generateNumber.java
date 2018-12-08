package drdm.school.pia.utils;

import java.util.Random;

/**
 * Class used for generating numbers.
 * @author Michal Drda
 */
public class generateNumber {

    public generateNumber() {

    }

    public static int getRandomNumber (int numberLength, String tableName, String columnName) {
        return generateRandomNumber(numberLength, tableName, columnName);
    }

    private static int generateRandomNumber(int numberLength, String tableName, String columnName) {
        int lowerBound = (int) Math.pow(10, (numberLength-1));
        int addition = (int) (Math.pow(10, (numberLength-1)) * 9);

        Random rnd = new Random();
        int randomNumber = (lowerBound + rnd.nextInt(addition));

        while (!isUniqueCheckDB(randomNumber, tableName, columnName)) {
            randomNumber = (lowerBound + rnd.nextInt(addition));
        }

        System.out.println("Username is: " + randomNumber);

        return randomNumber;
    }
    /*
        Should be somewhere in DAO (at least calling to DB, other logic can remain here).
     */
    private static boolean isUniqueCheckDB(int number, String tableName, String columnName) {
        String sqlQuery = "SELECT COUNT(" + columnName + ") FROM " + tableName + " WHERE " + columnName + " = '" + number + "';";
        //TODO check with DB that generated number does not exist yet (current is just a mock)
        if (sqlQuery.equals("1")) {
            //TODO uncoment this one.
            //return false;
            return true;
        }
        else {
            return true;
        }
    }

}
