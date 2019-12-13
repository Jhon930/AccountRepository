package com.account.ms.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.account.ms.models.Account;
import com.account.ms.models.SavingAccount;

public interface SavingAccountRepository extends ReactiveMongoRepository<SavingAccount, String>{

}
