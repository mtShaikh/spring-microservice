package com.foo.controller;

import com.foo.domain.Account;
import com.foo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.logging.Logger;

public class AccountController {

    @Autowired
    AccountRepository repository;

    protected Logger logger = Logger.getLogger(AccountController.class.getName());

    @RequestMapping(value = "/accounts/{number}", method = RequestMethod.GET)
    public Account findByNumber(@PathVariable("number") String number) {
        logger.info(String.format("Account.findByNumber(%s)", number));
        return repository.findByNumber(number);
    }

    @RequestMapping(value = "/accounts/customer/{customer}", method = RequestMethod.GET)
    public List<Account> findByCustomer(@PathVariable("customer") String customerId) {
        logger.info(String.format("Account.findByCustomer(%s)", customerId));
        return repository.findByCustomerId(customerId);
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public List<Account> findAll() {
        logger.info("Account.findAll()");
        return (List<Account>) repository.findAll();
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public Account add(@RequestBody Account account) {
        logger.info(String.format("Account.add(%s)", account));
        return repository.save(account);
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.PUT)
    public Account update(@RequestBody Account account) {
        logger.info(String.format("Account.update(%s)", account));
        return repository.save(account);
    }
}
