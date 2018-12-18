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

    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<E> persistedClass;

    /**
     *
     *
     * @param persistedClass entity type to be persisted by this instance
     */
    public GenericDaoJpa(Class<E> persistedClass) {
        this.entityManager = entityManager;
        this.persistedClass = persistedClass;
    }

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

    @Override
    public E findOne(PK id) {
        return entityManager.find(persistedClass, id);
    }

    @Override
    public void delete(PK id) {
        E en = entityManager.find(persistedClass, id);
        if(en != null) {
            entityManager.remove(en);
        }
    }

}
