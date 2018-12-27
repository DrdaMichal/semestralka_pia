package drdm.school.pia.dao.implementation;

import drdm.school.pia.dao.AccountDao;
import drdm.school.pia.domain.entities.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class AccountDaoJpa extends GenericDaoJpa<Account, Long> implements AccountDao {


    public AccountDaoJpa() {
        super(Account.class);
    }

    @Override
    public Account findByUserName(String username) {
        TypedQuery<Account> q = entityManager.createQuery("SELECT a FROM Account a LEFT JOIN User u ON a.id = u.account.id WHERE u.username = :username ", Account.class);
        q.setParameter("username", username);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        TypedQuery<Account> q = entityManager.createQuery("SELECT a FROM Account a WHERE a.number = :accountNumber", Account.class);
        q.setParameter("accountNumber", accountNumber);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Account merge(Account account) {
        entityManager.merge(account);
        return account;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }
}
