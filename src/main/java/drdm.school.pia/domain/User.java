package drdm.school.pia.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Entity representing application User.
 *
 * Date: 22.10.18
 *
 * @author Michal Drda
 */
public class User extends BaseObject implements UserDetails {
    /**
     * Login, unique
     */
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    /**
     * Secret for signing-in
     */
    private String password;
    private String address;
    private String zip;
    private String birthId;
    private Date birthDate;
    private String gender;

    public User() {
    }

    public User(String username, String firstname, String lastname, String email, String password, String address, String zip, String birthId, Date birthDate, String gender) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.zip = zip;
        this.birthId = birthId;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    /*
    ########### API ##################
     */

    /**
     * Validates that user instance is currently in a valid state.
     * @throws UserValidationException in case the instance is not in valid state.
     */
    public void validate() throws UserValidationException {
        if(StringUtils.isBlank(username)) throw new UserValidationException("Username is a required field");
        if(StringUtils.isBlank(firstname)) throw new UserValidationException("Firstname is a required field");
        if(StringUtils.isBlank(lastname)) throw new UserValidationException("Lastname is a required field");
        if(StringUtils.isBlank(email)) throw new UserValidationException("Email is a required field");
        if(StringUtils.isBlank(password)) throw new UserValidationException("Password is a required field");
        if(StringUtils.isBlank(address)) throw new UserValidationException("Address is a required field");
        if(StringUtils.isBlank(zip)) throw new UserValidationException("Zip Code is a required field");
        if(StringUtils.isBlank(birthId)) throw new UserValidationException("Birth id is a required field");
        if(StringUtils.isBlank(birthDate.toString())) throw new UserValidationException("Date of birth is a required field");
        if(StringUtils.isBlank(gender)) throw new UserValidationException("Gender is a required field");
    }

    /*
    ########### Spring Security ##################
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("role"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /*
    ########### MAPPINGS #####################
     */

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("username='").append(username).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
