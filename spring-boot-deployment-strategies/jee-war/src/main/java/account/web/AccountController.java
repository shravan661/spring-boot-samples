package account.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import account.domain.Account;
import account.domain.Customer;
import account.service.AccountService;

@Controller
class AccountController {

    @Autowired
    protected AccountService accountService;

    /**
     * METHOD A: RESTful method
     * TEST: 
     * curl -i -H 'Accept: application/json' http://localhost:8080/accounts?name=TheCustomerName
     */
    @RequestMapping(value="/accounts", produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Account> listWithMarshalling(Customer customer) {
        return accountService.findAllAccounts(customer.getName());
    }

    /**
     * METHOD C: RESTful method
     * TEST: 
     * curl -i -H 'Content-Type: application/json' -H 'Accept: application/json' -d '{"name":"TheCustomerName"}' http://localhost:8080/accounts
     */
    @RequestMapping(value="/accounts", produces="application/json", consumes="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Account> listWithMarshalling(@Valid @RequestBody Customer customer, BindingResult errors) {
        return accountService.findAllAccounts(customer.getName());
    }

    // ------

    /**
     * METHOD E: RESTful method
     * TEST: 
     * curl -i -H 'Accept: application/json' http://localhost:8080/accounts/{number}
     */
    @RequestMapping(value="/accounts/{number}", produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Account accountWithMarshalling(@PathVariable String number) {
        return accountService.findOne(number);
    }

}
