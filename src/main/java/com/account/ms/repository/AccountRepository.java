package com.account.ms.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.account.ms.models.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
	
	Flux<Account> findByPersonId(String id);
	
}
