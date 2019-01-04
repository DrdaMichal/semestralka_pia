package drdm.school.pia.domain;


import drdm.school.pia.domain.entities.User;
import drdm.school.pia.domain.exceptions.UserValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testValidate_Ok() {
        final String password = "Password";
        final String role = "Role";
        final String firstname = "Name";
        final String lastname = "Surname";
        final String email = "Email";
        final String address = "Address";
        final String city = "City";
        final String zip = "ZipCode";
        final String birthid = "BirthId";
        final String gender = "Gender";

        User u = new User(password, role, firstname, lastname, email, address, city, zip, birthid, gender);
        try {
            u.validate();
        } catch (UserValidationException e) {
            fail("Validation should have passed!");
        }
    }

    //the test will fail unless the exception is thrown
    @Test(expected = UserValidationException.class)
    public void testValidate_nullPwd() throws Exception {
        final String password = null;
        final String role = "Role";
        final String firstname = "Name";
        final String lastname = "Surname";
        final String email = "Email";
        final String address = "Address";
        final String city = "City";
        final String zip = "ZipCode";
        final String birthid = "BirthId";
        final String gender = "Gender";

        User u = new User(password, role, firstname, lastname, email, address, city, zip, birthid, gender);
        u.validate();
    }

    //the test will fail unless the exception is thrown
    @Test(expected = UserValidationException.class)
    public void testValidate_emptyPwd() throws Exception {
        final String password = "";
        final String role = "Role";
        final String firstname = "Name";
        final String lastname = "Surname";
        final String email = "Email";
        final String address = "Address";
        final String city = "City";
        final String zip = "ZipCode";
        final String birthid = "BirthId";
        final String gender = "Gender";

        User u = new User(password, role, firstname, lastname, email, address, city, zip, birthid, gender);
        u.validate();
    }

    //the test will fail if exception is thrown
    @Test
    public void testValidate_noUsername() throws Exception {
        final String password = "Password";
        final String role = "Role";
        final String firstname = "Name";
        final String lastname = "Surname";
        final String email = "Email";
        final String address = "Address";
        final String city = "City";
        final String zip = "ZipCode";
        final String birthid = "BirthId";
        final String gender = "Gender";

        User u = new User(password, role, firstname, lastname, email, address, city, zip, birthid, gender);
        u.validate();
    }

    //the test will fail when exception is thrown
    @Test
    public void testValidate_emptyUsername() throws Exception {
        final String password = "Password";
        final String role = "Role";
        final String firstname = "Name";
        final String lastname = "Surname";
        final String email = "Email";
        final String address = "Address";
        final String city = "City";
        final String zip = "ZipCode";
        final String birthid = "BirthId";
        final String gender = "Gender";

        User u = new User(password, role, firstname, lastname, email, address, city, zip, birthid, gender);
        u.validate();
    }

    //the test will fail unless the exception is thrown
    @Test(expected = UserValidationException.class)
    public void testValidate_empty() throws Exception {
        final String password = "";
        final String role = "";
        final String firstname = "";
        final String lastname = "";
        final String email = "";
        final String address = "";
        final String city = "";
        final String zip = "";
        final String birthid = "";
        final String gender = "";

        User u = new User(password, role, firstname, lastname, email, address, city, zip, birthid, gender);
        u.validate();
    }

    //the test will fail unless the exception is thrown
    @Test(expected = UserValidationException.class)
    public void testValidate_null() throws Exception {
        final String password = null;
        final String role = null;
        final String firstname = null;
        final String lastname = null;
        final String email = null;
        final String address = null;
        final String city = null;
        final String zip = null;
        final String birthid = null;
        final String gender = null;

        User u = new User(password, role, firstname, lastname, email, address, city, zip, birthid, gender);
        u.validate();
    }

    //the test will fail when exception is thrown
    @Test
    public void testValidate_notReqParameters() throws Exception {
        final String password = "psw";
        final String role = "role";
        final String firstname = "name";
        final String lastname = "surname";
        final String email = "email@cz.cz";
        final String address = null;
        final String city = null;
        final String zip = null;
        final String birthid = "abc";
        final String gender = "93";

        User u = new User(password, role, firstname, lastname, email, address, city, zip, birthid, gender);
        u.validate();
    }

}
