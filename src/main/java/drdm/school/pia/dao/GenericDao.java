package drdm.school.pia.dao;

import drdm.school.pia.domain.IEntity;

import java.io.Serializable;

/**
 * Common interface for all DAOs
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public interface GenericDao<E extends IEntity<PK>, PK extends Serializable> {

    /**
     * Either inserts new or updates existing instance.
     *
     * @param instance to be persisted
     * @return persisted instance
     */
    E save(E instance);

    /**
     *
     * @param id
     * @return instance with the given id or null if not found
     */
    E findOne(PK id);

    /**
     * Removes the given entity from persistence.
     *
     * @param id if of the entity instance
     */
    void delete(PK id);

}
