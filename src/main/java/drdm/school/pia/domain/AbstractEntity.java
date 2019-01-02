package drdm.school.pia.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Default entity object
 * @author Michal Drda
 */
public abstract class AbstractEntity {

    /**
     * Persistence link (used for Hibernate)
     */
    private static final String PERSISTENCE_UNIT = "drdm.school.pia";

}
