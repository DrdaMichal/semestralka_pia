package drdm.school.pia.domain;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public class BaseObject {

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
