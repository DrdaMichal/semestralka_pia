package drdm.school.pia.dao.implementation;

import drdm.school.pia.dao.GenericDao;
import drdm.school.pia.domain.IEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * JPA implementation of the GenericDao interface
 * @author Michal Drda
 */
public class GenericDaoJpa<E extends IEntity<PK>, PK extends Serializable> implements GenericDao<E, PK> {

    /**
     * Entity manager used
     */
    @PersistenceContext
    protected EntityManager entityManager;
    /**
     * Used for wiring by Spring framework
     */
    protected Class<E> persistedClass;

    /**
     * Constructor
     */
    public GenericDaoJpa(Class<E> persistedClass) {
        this.entityManager = entityManager;
        this.persistedClass = persistedClass;
    }

    /**
     * @inheritDoc
     * This is an implementation of save method in GenericDao interface
     */
    @Override
    public E save(E instance) {
        if (instance.getPK() == null) {
            entityManager.persist(instance);
            return instance;
        }
        else {
            // zmerguje vsechno se stejnym PK, proto muzeme pouzivat jen kdyz PK neexistuje -> novy zaznam
            return entityManager.merge(instance);
        }
    }

    /**
     * @inheritDoc
     * This is an implementation of findOne method in GenericDao interface
     */
    @Override
    public E findOne(PK id) {
        return entityManager.find(persistedClass, id);
    }

    /**
     * @inheritDoc
     * This is an implementation of delete method in GenericDao interface
     */
    @Override
    public void delete(PK id) {
        E en = entityManager.find(persistedClass, id);
        if(en != null) {
            entityManager.remove(en);
        }
    }

}
