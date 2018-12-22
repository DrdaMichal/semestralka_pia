package drdm.school.pia.dao;

import drdm.school.pia.domain.Account;

/**
 * DAO interface for the Account entity
 * @author Michal Drda
 */
public interface AccountDao extends GenericDao<Account, Long> {

    /**
     * Find role associated with a given user
     * @param username username of the user in scope
     * @return role associated with a given user
     */
    Account findByUserName(String username);

    /**
     * Find role by it's name
     * @param accountNumber account number provided
     * @return role with given name
     */
    Account findByAccountNumber(String accountNumber);

    /**
     * Merge account with the existing one
     * @param account provided account object
     * @return merged account
     */
    Account merge(Account account);

}
