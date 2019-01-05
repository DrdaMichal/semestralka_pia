package drdm.school.pia.dto.implementation;

import drdm.school.pia.dto.TransferObject;

import java.util.Objects;

/**
 * DTO used for filling table in user management page (managing)
 * @author Michal Drda
 */
public class UsersFetch implements TransferObject {

    /**
     * id of the transaction
     */
    private String id;
    /**
     * email of the user
     */
    private String email;
    /**
     * first name of the user
     */
    private String firstname;
    /**
     * Last name of the user
     */
    private String lastname;
    /**
     * birth id of the user
     */
    private String birthid;
    /**
     * gender of the user
     */
    private String gender;
    /**
     * Account of the user
     */
    private String account;

    /**
     * Default constructor
     */
    public UsersFetch() {}

    /**
     * Constructor
     * @param id provided id
     * @param email provided email
     * @param firstname provided first name
     * @param lastname provided last name
     * @param birthid provided birth id
     * @param gender provided gender
     * @param account provided account (along with prefix and bankcode)
     */
    public UsersFetch(String id, String email, String firstname, String lastname, String birthid, String gender, String account) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthid = birthid;
        this.gender = gender;
        this.account = account;
    }

    /**
     * @inheritDoc
     * Getter for id of UserFetch object
     * @return id
     */
    @Override
    public String getId() {
        return null;
    }

    /**
     * Setter for id of UserFetch object
     * @param id provided id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for an e-mail
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for an e-mail
     * @param email email provided
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for a first name
     * @return first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Setter for a first name
     * @param firstname provided first name
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Getter for a last name
     * @return  last name
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Setter for a last name
     * @param lastname provided last name
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Getter for a birth id
     * @return  birth id
     */
    public String getBirthid() {
        return birthid;
    }

    /**
     * Setter for a birth id
     * @param birthid provided birth id
     */
    public void setBirthid(String birthid) {
        this.birthid = birthid;
    }

    /**
     * Getter for a gender
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Setter for a gender
     * @param gender provided gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Getter for an account
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     * Setter for an account
     * @param account provided account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @inheritDoc
     * Used for writing the object to string
     * @return string of the object serialization
     */
    @Override
    public String toString() {
        return "UsersFetch{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthid='" + birthid + '\'' +
                ", gender='" + gender + '\'' +
                ", account='" + account + '\'' +
                '}';
    }

    /**
     * @inheritDoc
     * Used for equation of the object with another one
     * @param o object to be equalized
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersFetch that = (UsersFetch) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(birthid, that.birthid) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(account, that.account);
    }

    /**
     * @inheritDoc
     * Hash code
     * @return hash int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstname, lastname, birthid, gender, account);
    }

}
