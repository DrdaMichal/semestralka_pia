package drdm.school.pia.domain;

/**
 * Default object class
 * @author Michal Drda
 */
public class BaseObject extends AbstractEntity {

    /**
     * Id of the object in the database
     */
    private Long id;

    /**
     * A method used for checking if Id already exists in the persistence unit
     * @return true if the entity hasn't been persisted yet
     */
    public boolean isNew() {
        return id == null;
    }

    /**
     * Getter for object id
     * @return object id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for object id
     * @param id id provided to be set
     */
    public void setId(Long id) {
        this.id = id;
    }

}
