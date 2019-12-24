package com.account.ms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.http.MediaType.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.account.ms.mapper.AccountMapper;
import com.account.ms.models.Account;
import com.account.ms.models.Person;
import com.account.ms.models.SavingAccount;
import com.account.ms.models.services.AccountService;
import com.account.ms.repository.AccountRepository;
import com.account.ms.repository.SavingAccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AccountController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private SavingAccountRepository saRepository;
	
	@Autowired
    private WebClient.Builder webClientBuilder;

	@GetMapping("/person/{person}")
	public Flux<Account> findByPerson(@PathVariable("person") String personDni) {
		LOGGER.info("findByPerson: dni={}", personDni);
		return repository.findByPersonDni(personDni);
	}

	@GetMapping
	public Flux<Account> findAll() {
		LOGGER.info("findAll");
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public Mono<Account> findById(@PathVariable("id") String id) {
		LOGGER.info("findById: id={}", id);
		return repository.findById(id);
	}

	@PostMapping
	public Mono<Account> create(@RequestBody Account account) {
		LOGGER.info("create: {}", account);
		return repository.save(account);
	}
	
	@PostMapping("/insert")
	public Mono<Account> insertAccount(@RequestBody Account data ){
		return webClientBuilder.build().post().uri("http://localhost:8084/savingaccount/insert").syncBody(data)
				.retrieve().bodyToMono(Account.class);
	}
		
	@GetMapping("/account/{numaccount}/clients")
	public Mono<Account> findClientsByNumberAccount(@PathVariable("numaccount") String numaccount) {
		
		LOGGER.info("findByNumberAccountWithClients: numaccount={}", numaccount);
		Flux<Person> clients = webClientBuilder.build().get().uri("http://localhost:8008/account/{account}", numaccount).retrieve().bodyToFlux(Person.class);		
		return clients
				.collectList()
				.map(c -> new Account(c))
				.mergeWith(repository.findPersonByNumberAccount(numaccount))
				.collectList()
				.map(AccountMapper::map);
	}
	
}
