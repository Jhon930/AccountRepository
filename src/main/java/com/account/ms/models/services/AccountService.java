package com.account.ms.models.services;

import com.account.ms.models.Account;
import com.account.ms.models.SavingAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
	
	public Flux<Account> findAll();
	
	public Mono<Account> findById(String id);
	
    public Mono<Account> save(Account account);
    
    //public Mono<SavingAccount> saveAccount(SavingAccount savingAccount);
    
    public Flux<Account> findByPersonId(String id);
    
    public Mono<Account> update(Account account, String id);
    
    //public Mono<Void> delete(String id);
    
}
