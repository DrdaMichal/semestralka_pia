package drdm.school.pia.dao;

import drdm.school.pia.domain.entities.Account;

/**
 * DAO interface for the Account entity
 * @author Michal Drda
 */
public interface AccountDao extends GenericDao<Account, Long> {

    /**
     * Find account associated with a given user name
     * @param username user name of the user provided
     * @return account associated with a user with given user name or null
     */
    Account findByUserName(String username);

    /**
     * Find account with given account number
     * @param accountNumber account number provided
     * @return account object with given account number or null
     */
    Account findByAccountNumber(String accountNumber);

}
