package account.service;

import java.util.List;

import account.domain.Account;

public interface AccountService {

    /**
     * Finds the account with the provided account number.
     * 
     * @param number The account number
     * @return The account
     * @throws AccountNotFoundException If no such account exists.
     */
    Account findOne(String number) throws AccountNotFoundException;

    /**
     * Return a listing of all accounts for a given customer.
     * 
     * @param owner
     *            Name of the owner of the accounts.
     * @return the account listing
     */
    List<Account> findAllAccounts(String owner);

    /**
     * Saves the given entity.
     * 
     * @param account
     */
    void save(Account account);

    /**
     * Creates a new account.
     * 
     * @param account
     * @return created account
     */
    Account create(Account account);
}
