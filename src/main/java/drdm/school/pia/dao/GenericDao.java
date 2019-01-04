package drdm.school.pia.dao;

import drdm.school.pia.domain.IEntity;

import java.io.Serializable;

/**
 * Common interface for all DAOs
 * @author Michal Drda
 */
public interface GenericDao<E extends IEntity<PK>, PK extends Serializable> {

    /**
     * Either inserts new or updates existing instance.
     * @param instance to be persisted
     * @return persisted instance
     */
    E save(E instance);

    /**
     * Finds an object by it's id
     * @param id
     * @return instance with the given id or null if not found
     */
    E findOne(PK id);

    /**
     * Removes the given entity from persistence.
     * @param id of the entity instance
     */
    void delete(PK id);

    /**
     * Removes the entity from persistence context to be loaded from DB next time
     * @param id of the entity instance
     */
    void removeFromContext(PK id);

}
