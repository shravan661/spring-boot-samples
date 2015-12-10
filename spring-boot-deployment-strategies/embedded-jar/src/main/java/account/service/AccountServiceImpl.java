package account.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import account.domain.Account;

@Service
public class AccountServiceImpl implements AccountService {

    /**
     * {@inheritDoc}
     * <p/>
     * Dummy method for testing purposes.
     * 
     * @param number The account number. Set 0000 to get an {@link AccountNotFoundException} 
     */
    @Override
    public Account findOne(String number) throws AccountNotFoundException {
        // dummy method
        if(number.equals("0000")) {
            throw new AccountNotFoundException("0000");
        }
        return new Account(0, number);
    }

    @Override
    public void save(Account account) {
        // dummy method
    }

    @Override
    public Account create(Account account) {
        account.setId(new Integer(1));
        return account;
    }

    @Override
    public List<Account> findAllAccounts(String owner) {
        List<Account> accounts = new ArrayList<Account>();
        accounts.add(new Account(1, "1111"));
        return accounts;
    }

}
