package com.account.ms.models.services;

import com.account.ms.models.Account;
import com.account.ms.models.Person;
import com.account.ms.models.SavingAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
	
	public Flux<Account> findAll();
	
	public Mono<Account> findById(String id);
	
    public Mono<Account> save(Account account);
    
    //public Mono<SavingAccount> saveAccount(SavingAccount savingAccount);
    
    public Mono<Account> findByPersonDni(String dni);
    
    public Mono<Account> update(Account account, String id);
    
    //public Mono<Void> delete(String id);
}


