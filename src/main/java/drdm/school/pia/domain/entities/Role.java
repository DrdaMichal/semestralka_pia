package drdm.school.pia.domain.entities;

import drdm.school.pia.domain.BaseObject;
import drdm.school.pia.domain.IEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Role entity, used for storing role attached to user
 * @author Michal Drda
 */
@Entity
@Table(name="drdam_role")
public class Role extends BaseObject implements IEntity<Long>, Serializable {

    /**
     * Name of the role (used globally in the application as an identification)
     */
    private String name;
    /**
     * A generated ID of the entity
     */
    private Long id;
    /**
     * Set of users that has this role assigned
     */
    private Set<User> users;

    /**
     * Default constructor
     */
    public Role() {
    }

    /**
     * Constructor of the class
     * @param name provided role name
     */
    public Role(String name) {
        this.name = name;
    }

    /**
     * @inheritDoc
     * Primary key getter implementation
     */
    @Transient
    @Override
    public Long getPK() { return getId(); }

    /**
     * Primary key of the role, getter
     * @return id - primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id id provided to be set
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Getter for the role name
     * @return role name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for a role name
     * @param name role name provided
     */
    public void setName(String name) { this.name = name; }

    /**
     * OneToMany association between user and it's roles.
     * Multiple users may be attached to one role, Role may have multiple users
     * Getter for Users list linked to Role
     * @return User list linked to Role
     */
    @OneToMany(targetEntity = User.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "role")
    public Set<User> getUsers() {return users; }

    /**
     * Setter of users using this role
     * @param users provided users set
     */
    public void setUsers(Set<User> users) {this.users = users;}

    /**
     * Generated equals method
     * @param o provided object to be compared
     * @return true if objects are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return !(name != null ? !name.equals(role.name) : role.name != null);

    }

    /**
     * Generated hash code method
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    /**
     * Generated toString method
     * @return Card printed to string
     */
    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

}
