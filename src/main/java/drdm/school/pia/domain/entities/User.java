package drdm.school.pia.domain.entities;

import drdm.school.pia.domain.BaseObject;
import drdm.school.pia.domain.IEntity;
import drdm.school.pia.domain.exceptions.UserValidationException;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity representing application User
 * @author Michal Drda
 */
@Entity
@Table(name = "drdam_user")
public class User extends BaseObject implements IEntity<String>, Serializable {
    /**
     * Login, unique
     */
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String roleName;
    private Role role;

    /**
     * Secret password to login
     */
    private String password;
    private String address;
    private String city;
    private String zip;
    private String birthId;
    private String gender;
    private Account account;


    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * Main constructor
     * @param password
     * @param role
     * @param firstname
     * @param lastname
     * @param email
     * @param address
     * @param city
     * @param zip
     * @param birthId
     * @param gender
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

    @Override
    @Transient
    public String getPK() {
        return getUsername();
    }

    /*
    ########### API ##################
     */

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

    /*
    ########### Spring Security ##################
     */

/*    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }*/

    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    public boolean isEnabled() {
        return true;
    }

    /*
    ########### MAPPINGS #####################
     */

    @Id
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * ManyToOne association between user and his role.
     *
     * Role may be attached to multiple users, User may have one role
     * thus the ManyToOne
     * @return
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    public Role getRole() {
        return role; }

    public void setRole(Role role){ this.role = role; }

    public void setRole(String roleName) {
        this.role = new Role(roleName);
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Transient
    public String getRoleName() { return roleName; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setBirthId(String birthId) {
        this.birthId = birthId;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getZip() {
        return zip;
    }
    public String getBirthId() {
        return birthId;
    }
    public String getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return !(username != null ? !username.equals(user.username) : user.username != null);

    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

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
