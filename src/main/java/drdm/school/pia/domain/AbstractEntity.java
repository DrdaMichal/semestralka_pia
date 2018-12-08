package drdm.school.pia.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Michal Drda
 */
public abstract class AbstractEntity {

    private static final String PERSISTENCE_UNIT = "drdm.school.pia";

    //init
    EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    EntityManager em = factory.createEntityManager();

}
