package com.foo.repository;

import com.foo.domain.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, String> {

    public Account findByNumber(String number);
    public List<Account> findByCustomerId(String customerId);
}
