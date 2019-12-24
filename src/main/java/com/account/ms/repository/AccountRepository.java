package com.account.ms.repository;

import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.account.ms.models.Account;
import com.account.ms.models.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
	
    @Query(value="{'persons.dni': ?0}")	
	Flux<Account> findByPersonDni(String dni); 
    
	@Query(value="{'accounts.numberAccount': ?0}")
	Flux<Account> findPersonByNumberAccount(String number);
  
    
    
	
}
