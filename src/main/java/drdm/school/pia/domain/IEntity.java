package drdm.school.pia.domain;

import java.io.Serializable;

/**
 * Base interface for all entities to make implementation of generic dao easier.
 * PK type represents type of the entity's primary key.
 * @author Michal Drda
 */
public interface IEntity<PK extends Serializable> {

    /**
     * Getter to get a primary key of the entity
     * @return entity primary key
     */
    PK getPK();

}
