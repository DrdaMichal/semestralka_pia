package drdm.school.pia.domain;

/**
 * @author Michal Drda
 */
public class BaseObject extends AbstractEntity {

    private Long id;

    /**
     *
     * @return true if the entity hasn't been persisted yet
     */
    public boolean isNew() {
        return id == null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
