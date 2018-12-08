package drdm.school.pia.dao;

import drdm.school.pia.domain.IEntity;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * JPA implementation of the GenericDao interface
 * @author Michal Drda
 */
public class GenericDaoJpa<E extends IEntity<PK>, PK extends Serializable> implements GenericDao<E, PK> {

    protected EntityManager entityManager;
    protected Class<E> persistedClass;

    /**
     *
     * @param em entity manager
     * @param persistedClass entity type to be persisted by this instance
     */
    public GenericDaoJpa(EntityManager em, Class<E> persistedClass) {
        this.entityManager = em;
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
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public E findOne(PK id) {
        return entityManager.find(persistedClass, id);
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(PK id) {
        E en = entityManager.find(persistedClass, id);
        if(en != null) {
            entityManager.remove(en);
        }
        //throw new UnsupportedOperationException("Not implemented yet");
    }

}
