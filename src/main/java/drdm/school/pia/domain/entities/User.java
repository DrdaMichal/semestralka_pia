package drdm.school.pia.domain.entities;

import drdm.school.pia.domain.BaseObject;
import drdm.school.pia.domain.IEntity;
import drdm.school.pia.domain.exceptions.UserValidationException;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity represention of the application User
 * @author Michal Drda
 */
@Entity
@Table(name = "drdam_user")
public class User extends BaseObject implements IEntity<String>, Serializable {
    /**
     * Login, unique
     */
    private String username;
    /**
     * First name of the user
     */
    private String firstname;
    /**
     * Last name of the user
     */
    private String lastname;
    /**
     * E-mail of the user
     */
    private String email;
    /**
     * Role name of the user
     * Used to get the role name from the servlet
     */
    private String roleName;
    /**
     * Role object linked to the User instance
     */
    private Role role;
    /**
     * Secret password to login
     */
    private String password;
    /**
     * Address of the user
     */
    private String address;
    /**
     * City of the user
     */
    private String city;
    /**
     * Zip code of the user
     */
    private String zip;
    /**
     * Birth id of the user
     */
    private String birthId;
    /**
     * Gender of the user
     */
    private String gender;
    /**
     * Account object linked to the User instance
     */
    private Account account;

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * Main constructor
     * @param password provided secret password
     * @param role provided role name
     * @param firstname provided first name
     * @param lastname provided last name
     * @param email provided e-mail
     * @param address provided address
     * @param city provided city
     * @param zip provided zip code
     * @param birthId provided birth id
     * @param gender provided gender
     */
    public User(String password, String role, String firstname, String lastname, String email, String address, String city, String zip, String birthId, String gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.roleName = role;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.birthId = birthId;
        this.gender = gender;
    }

    /**
     * @inheritDoc
     * Primary key getter implementation
     */
    @Override
    @Transient
    public String getPK() {
        return getUsername();
    }

    /**
     * Validates that user instance is currently in a valid state.
     * @throws UserValidationException in case the instance is not in valid state.
     */
    public void validate() throws UserValidationException {
        if(StringUtils.isBlank(firstname)) throw new UserValidationException("First name is a required field");
        if(StringUtils.isBlank(lastname)) throw new UserValidationException("Last name is a required field");
        if(StringUtils.isBlank(email)) throw new UserValidationException("E-mail is a required field");
        if(StringUtils.isBlank(password)) throw new UserValidationException("Password is a required field");
        if(StringUtils.isBlank(roleName)) throw new UserValidationException("Role is a required field");
        if(StringUtils.isBlank(birthId)) throw new UserValidationException("Birth id is a required field");
        if(StringUtils.isBlank(gender)) throw new UserValidationException("Gender is a required field");
    }

    /**
     * Primary key of the user, getter
     * @return user name primary key
     */
    @Id
    public String getUsername() {
        return username;
    }

    /**
     * Setter for primary key
     * @param username provided user name
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * ManyToOne association between user and his role.
     * Role may be attached to multiple users, User may have one role
     * Getter for role object linked to user
     * @return role instance linked to user
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    public Role getRole() {
        return role; }

    /**
     * Setter for the role associated with the user
     * @param role provided role object
     */
    public void setRole(Role role){ this.role = role; }

    /**
     * Setter for the role object associated with the user
     * @param roleName provided role name, to assign new Role
     */
    public void setRole(String roleName) {
        this.role = new Role(roleName);
    }

    /**
     * Getter for role name (not stored in the database)
     * @return role name added to user
     */
    @Transient
    public String getRoleName() { return roleName; }

    /**
     * OneToOne association between user and his account
     * Account may be attached to single user, User has one account
     * Getter for account object linked to user
     * @return account instance linked to user
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    public Account getAccount() {
        return account;
    }

    /**
     * Setter to set account instance to the user
     * @param account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Getter for secret password
     * @return password string
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for secret password
     * @param password provided password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter for the first name
     * @param firstname provided first name
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Setter for the last name
     * @param lastname provided last name
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Setter for the email
     * @param email provided e-mail
     */
    @Column(unique = true)
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter for the address
     * @param address provided address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Setter for the city
     * @param city provided city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Setter for the zip code
     * @param zip provided zip code
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Setter for the birth Id
     * @param birthId setter for the birth id
     */
    public void setBirthId(String birthId) {
        this.birthId = birthId;
    }

    /**
     * Setter for the gender
     * @param gender provided gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Getter to get a first name of the user
     * @return user's first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Getter to get a last name of the user
     * @return user's last name
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Getter to get a email of the user
     * @return user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter to get a address line (user address)
     * @return user's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter to get a city where the user lives
     * @return user's city
     */
    public String getCity() {
        return city;
    }

    /**
     * Getter to get a zip code of the user address
     * @return user's zip code
     */
    public String getZip() {
        return zip;
    }

    /**
     * Getter to get a birth id of the user
     * @return user's birth id
     */
    public String getBirthId() {
        return birthId;
    }

    /**
     * Getter to get a gender of the user
     * @return user's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Generated equals method
     * @param o provided object to be compared
     * @return true if objects are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return !(username != null ? !username.equals(user.username) : user.username != null);

    }

    /**
     * Generated hash code method
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    /**
     * Generated toString method
     * @return user printed to string
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", roleName='" + roleName + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", birthId='" + birthId + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

}
