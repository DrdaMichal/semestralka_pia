package drdm.school.pia.domain;

import javax.persistence.*;

/**
 * @author Michal Drda
 */
@Entity
@Table(name="drdm_user_role_pia")
public class Role implements IEntity<String> {

    private String name;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Role{");
        sb.append(", name='").append(name).append('\'');
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
