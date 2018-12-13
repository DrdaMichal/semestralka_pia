package drdm.school.pia.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Michal Drda
 */
@Entity
@Table(name="drdam_role")
public class Role implements IEntity<Long> {

    private String name;
    private Long id;
    private Set<User> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @Transient
    @Override
    public Long getPK() {
        return getId();
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade=CascadeType.ALL)
    public Set<User> getUsers() {return users; }

    public void setUsers(Set<User> users) {this.users = users;}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Role{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

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
}
