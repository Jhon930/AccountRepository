package com.account.ms.models.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.http.MediaType.*;
import org.springframework.stereotype.Service;
// import static org.springframework.web.reactive.function.BodyInserters.*;
import org.springframework.web.reactive.function.client.WebClient;

import com.account.ms.models.Account;
import com.account.ms.models.Person;
import com.account.ms.models.SavingAccount;
import com.account.ms.repository.AccountRepository;
import com.account.ms.repository.SavingAccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private SavingAccountRepository srepository;
	
	@Autowired
	private WebClient client;

	@Override
	public Flux<Account> findAll() {
		// TODO Auto-generated method stub
		return client.get().accept(MediaType.APPLICATION_JSON_UTF8)
						   .exchange()
						   .flatMapMany(response-> response.bodyToFlux(Account.class));
	}
	
	
	@Override
	public Mono<Account> findById(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return client.get().uri("/{id}", params)
				.accept(APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Account.class);
				//.exchange()
				//.flatMap(response -> response.bodyToMono(Account.class));
	}
	
	
	@Override
	public Mono<Account> findByPersonDni(String dni) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dni", dni);
		return client.get().uri("/{dni}", params)
				.accept(APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Account.class);
				//.exchange()
				//.flatMap(response -> response.bodyToMono(Account.class));
	}

	@Override
	public Mono<Account> save(Account account) {
		// TODO Auto-generated method stub
		return repository.save(account);
	}

	/*@Override
	public Mono<SavingAccount> saveAccount(SavingAccount savingAccount) {
		// TODO Auto-generated method stub
		return srepository.save(savingAccount);
	}*/


	/*@Override
	public Mono<Void> delete(Account account) {
		// TODO Auto-generated method stub
		return repository.delete(account);
	}*/

	@Override
	public Mono<Account> update(Account account, String id) {
		// TODO Auto-generated method stub
		return repository.save(account);
	}

	/*@Override
	public Flux<Account> findAll() {
		return client.get().accept(APPLICATION_JSON)
				.exchange()
				.flatMapMany(response -> response.bodyToFlux(Account.class));
	}



	@Override 
	public Mono<Account> save(Account account) {
		return client.post()
				.accept(APPLICATION_JSON_UTF8)
				.contentType(APPLICATION_JSON_UTF8)
				//.body(fromObject(account))
				.syncBody(account)
				.retrieve()
				.bodyToMono(Account.class);
	}

	@Override
	public Mono<Account> update(Account account, String id) {
		
		return client.put()
				.uri("/{id}", Collections.singletonMap("id", id))
				.accept(APPLICATION_JSON_UTF8)
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(account)
				.retrieve()
				.bodyToMono(Account.class);
	}

	@Override
	public Mono<Void> delete(String id) {
		return client.delete().uri("/{id}", Collections.singletonMap("id", id))
				.retrieve()
				.bodyToMono(Void.class);
	}*/


}
