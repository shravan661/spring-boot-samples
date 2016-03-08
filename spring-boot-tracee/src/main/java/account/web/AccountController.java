/*
 * Copyright (C) 2015 DISID CORPORATION S.L.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see `<http://www.gnu.org/licenses/>`.
 */
package account.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import account.domain.Account;
import account.service.AccountService;
import io.tracee.Tracee;
import io.tracee.TraceeBackend;
import io.tracee.TraceeConstants;

@Controller
class AccountController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    protected AccountService accountService;

    /**
     * See section `Run Tests` at README.adoc to learn how to test.
     */
    @RequestMapping(value="/accounts", produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Account getAccount(@RequestParam String number) {
        Account account = accountService.findOne(number);
        TraceeBackend backend = Tracee.getBackend();
        // Try to get the variable 'NUMBER'. Check that the variable is not 
        // passed between different 'sessions' of TracEE
        if(Tracee.getBackend().get("NUMBER") != null){
          LOG.error("*** NUMBER: ".concat(backend.get("NUMBER")));
        }
        // put variable 'NUMBER' inside backend
        backend.put("NUMBER", "1234");     
        LOG.info("Account found: ".concat(account.toString()));
        // Get invocation Id
        String invocationId = backend.get(TraceeConstants.INVOCATION_ID_KEY);
        LOG.error("*** Get Account invocationId: ".concat(invocationId));
        LOG.error("Account found: ".concat(account.toString()));
        return account;
    }
    
    /**
     * See section `Run Tests` at README.adoc to learn how to test.
     * 
     * In this method put parameter into backend before to call findOne method.
     */
    @RequestMapping(value="/accounts2", produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Account getAccount2(@RequestParam String number) {
        TraceeBackend backend = Tracee.getBackend();
        // put variable 'NUMBER' inside backend
        backend.put("NUMBER", "5678");     
        Account account = accountService.findOne(number);        
        LOG.info("Account found: ".concat(account.toString()));
        // Get invocation Id
        String invocationId = backend.get(TraceeConstants.INVOCATION_ID_KEY);
        LOG.error("*** Get Account 2 invocationId: ".concat(invocationId));
        LOG.error("Account found: ".concat(account.toString()));
        return account;
    }
    

    /**
     * See section `Run Tests` at README.adoc to learn how to test.
     */
    @RequestMapping(value="/accounts")
    public String getAccount(@RequestParam String number, Model model) {
        Account account = accountService.findOne(number);

        model.addAttribute( account );

        // Return the view to use for rendering the response
        return "accounts/show";
    }
}
