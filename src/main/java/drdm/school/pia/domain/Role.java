package drdm.school.pia.domain;

import javax.persistence.*;
import java.util.List;

/**
 * @author Michal Drda
 */
@Entity
@Table(name="drdam_role")
public class Role implements IEntity<String> {

    private String name;
    private List<User> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @Transient
    @Override
    public String getPK() {
        return getName();
    }
    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "role")
    public List<User> getUsers() {return users; }

    public void setUsers(List<User> users) {this.users = users;}

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
