package drdm.school.pia.domain.entities;

import drdm.school.pia.domain.BaseObject;
import drdm.school.pia.domain.IEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Michal Drda
 */
@Entity
@Table(name="drdam_role")
public class Role extends BaseObject implements IEntity<Long>, Serializable {

    private String name;

    /**
     * A generated ID of the entity
     */
    private Long id;

    private Set<User> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @Transient
    @Override
    public Long getPK() { return getId(); }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    @OneToMany(targetEntity = User.class, cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
    public Set<User> getUsers() {return users; }

    public void setUsers(Set<User> users) {this.users = users;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return !(name != null ? !name.equals(role.name) : role.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

}
