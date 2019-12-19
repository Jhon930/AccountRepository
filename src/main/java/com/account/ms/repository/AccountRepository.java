package com.account.ms.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.account.ms.models.Account;
import com.account.ms.models.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
	
    @Query(value= "{'accounts.person.dni': ?0}")	
	Mono<Account> findByPersonDni(String dni);
	
}
