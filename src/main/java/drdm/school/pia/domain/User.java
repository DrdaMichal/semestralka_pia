package drdm.school.pia.domain;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entity representing application User.
 *
 * Date: 22.10.18
 *
 * @author Michal Drda
 */
@Entity
@Table(name = "pia_drdm_user")
public class User implements IEntity<String> {
    /**
     * Login, unique
     */
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
    /**
     * Secret for signing-in
     */
    private String password;
    private String address;
    private String city;
    private String zip;
    private String birthId;
    private String gender;

    public User(String username, String password, String role, String firstname, String lastname, String email, String address, String city, String zip, String birthId, String gender) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
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

    private Set<Role> roles;

    public User() {
        this.roles = new LinkedHashSet<>();
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
        if(StringUtils.isBlank(firstname)) throw new UserValidationException("First name is a required field");
        if(StringUtils.isBlank(lastname)) throw new UserValidationException("Last name is a required field");
        if(StringUtils.isBlank(email)) throw new UserValidationException("E-mail is a required field");
        if(StringUtils.isBlank(password)) throw new UserValidationException("Password is a required field");
        if(StringUtils.isBlank(role)) throw new UserValidationException("Role is a required field");
        if(StringUtils.isBlank(address)) throw new UserValidationException("Address is a required field");
        if(StringUtils.isBlank(city)) throw new UserValidationException("City is a required field");
        if(StringUtils.isBlank(zip)) throw new UserValidationException("Zip Code is a required field");
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


    public boolean isAccountNonExpired() {
        return true;
    }


    public boolean isAccountNonLocked() {
        return true;
    }


    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return true;
    }

    /*
    ########### MAPPINGS #####################
     */

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * ManyToMany association between user and his roles.
     *
     * -- Role may be attached to multiple users, User may have multiple roles
     * thus the ManyToMany
     * @return
     */
    @ManyToMany
    @JoinTable(name = "pia_drdm_user_roles", joinColumns = @JoinColumn(name = "user", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name= "role", referencedColumnName = "id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
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
    public String getBirthid() {
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
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("username='").append(username).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
