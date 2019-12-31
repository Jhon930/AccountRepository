package com.account.ms.repository;

import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.account.ms.models.Account;
import com.account.ms.models.PersonClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
	
    @Query(value="{'personclients.dni': ?0}")	
	Mono<Account> findByPersonDni(String dni); 
    
	Mono<Account> findByNumberAccount(String number);
	
}
