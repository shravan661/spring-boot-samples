package account.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
     * METHOD B: View-based method
     * TEST: 
     * curl -i http://localhost:8080/accounts?name=TheCustomerName
     */
    @RequestMapping("/accounts")
    public String listWithView(Model model, Customer customer) {

        // Call RESTful method to avoid repeating account lookup logic
        model.addAttribute( listWithMarshalling(customer) );

        // Return the view to use for rendering the response
        return "accounts/list";
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

    /**
     * METHOD D: View-based method
     * TEST: 
     * curl -i -H 'Content-Type: application/json' -d '{"name":"TheCustomerName"}' http://localhost:8080/accounts
     */
    @RequestMapping(value="/accounts", consumes="application/json")
    public String listWithView(Model model, @Valid @RequestBody Customer customer, BindingResult errors) {

        // Call RESTful method to avoid repeating account lookup logic
        model.addAttribute( listWithMarshalling(customer) );

        // Return the view to use for rendering the response
        return "accounts/list";
    }
}