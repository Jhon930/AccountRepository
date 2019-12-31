package com.account.ms.models.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.http.MediaType.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
// import static org.springframework.web.reactive.function.BodyInserters.*;
import org.springframework.web.reactive.function.client.WebClient;

import com.account.ms.models.Account;
import com.account.ms.models.PersonClient;
import com.account.ms.models.SavingAccount;
import com.account.ms.repository.AccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository repository;
	
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
		return repository.findByPersonDni(dni);
		
	}
	
	@Override
	public Mono<Account> save(Account account) {
		// TODO Auto-generated method stub
		return repository.save(account);
	}

	@Override
	public Mono<Account> update(Account account, String id) {
		// TODO Auto-generated method stub
		return repository.save(account);
	}

	@Override
	public Mono<Account> findByNumberAccount(String number) {
		// TODO Auto-generated method stub
		return repository.findByNumberAccount(number);
	}


}
