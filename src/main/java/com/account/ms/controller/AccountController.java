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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AccountController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private AccountRepository repository;
	
	@Autowired
    private WebClient.Builder webClientBuilder;

	@GetMapping("/person/{person}")
	public Flux<Account> findByPerson(@PathVariable("person") String personDni) {
		LOGGER.info("findByCustomer: dniPerson={}", personDni);
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
	
	@GetMapping("/person/{dni}/clients")
	public Mono<Account> findClientsByNumberAccount(@PathVariable("numaccount") String numaccount) {
		
		LOGGER.info("findByNumberAccountWithClients: numaccount={}", numaccount);
		Flux<Person> clients = webClientBuilder.build().get().uri("http://localhost:8070/person/{person}", numaccount).retrieve().bodyToFlux(Person.class);		
		return clients
				.collectList()
				.map(c -> new Account(c))
				.mergeWith(repository.findPersonByNumberAccount(numaccount))
				.collectList()
				.map(AccountMapper::map);
	}


	
	
	/*@Autowired
	private AccountService service;
	
	@Autowired
	private AccountRepository repository;
	
	@GetMapping
	public Mono<ServerResponse> listar(ServerRequest request){
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(service.findAll(), Account.class);
		 
	}
	
	@GetMapping("/{id}")
	public Mono<ServerResponse> show(ServerRequest request){
		String id = request.pathVariable("id");
		return errorHandler(
				service.findById(id).flatMap(p-> ServerResponse.ok()
						.contentType(APPLICATION_JSON_UTF8)
						.syncBody(p))
						.switchIfEmpty(ServerResponse.notFound().build())
				
				);
				
	}
	
	@GetMapping("/person/{person}")
	public Mono<ServerResponse> findByPerson(ServerRequest request){
		String dni = request.pathVariable("dni");
		return errorHandler(
				service.findByPersonDni(dni).flatMap(p-> ServerResponse.ok()
						.contentType(APPLICATION_JSON_UTF8)
						.syncBody(p))
						.switchIfEmpty(ServerResponse.notFound().build())
			   );
	}
	
	
	/*@GetMapping("/person/{person}")
	public Flux<Account> findByPerson(@PathVariable("person") String personId) {
		return service.findByPersonId(personId);
	}
	
	@GetMapping
	public Flux<Account> findAll(){
	      return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Account> findById(@PathVariable("id") String id){
		return service.findById(id);
	}
	
	@PostMapping
	public Mono<Account> create(@RequestBody Account account){
		return service.save(account);
	}
	
	@PutMapping("/updateAccount/{id}")
    public Mono<Account> insertDeposit(@PathVariable("id") final String id, @RequestBody final Account account){
  
        BigDecimal balance = account.getCurrentBalance();
        BigDecimal deposit = account.getDeposit();
        //BigDecimal withdraw = new BigDecimal("50.00");
    	deposit = balance.add(deposit);
    	//withdraw = balance.subtract(withdraw);
    	
    	account.setCurrentBalance(balance);
    	
    	return service.update(account, id);
 
    }
    
    @PutMapping("/updateAccountWithdraw/{id}")
    public Mono<Account> insertWithdraw(@PathVariable("id") final String id, @RequestBody final Account account){
  
        BigDecimal balance = account.getCurrentBalance();
        BigDecimal withdraw = account.getWithdraw();
        //BigDecimal withdraw = new BigDecimal("50.00");
    	withdraw = balance.subtract(withdraw);
    	//withdraw = balance.subtract(withdraw);
    	balance = withdraw;
    	account.setCurrentBalance(balance);
    	
    	return service.update(account, id);
 
    }
	*/
	/*
	private Mono<ServerResponse> errorHandler(Mono<ServerResponse> response){
		return response.onErrorResume(error -> {
			WebClientResponseException errorResponse = (WebClientResponseException) error;
			if(errorResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
				Map<String, Object> body = new HashMap<>();
				body.put("error", "No existe el producto: ".concat(errorResponse.getMessage()));
				body.put("timestamp", new Date());
				body.put("status", errorResponse.getStatusCode().value());
				return ServerResponse.status(HttpStatus.NOT_FOUND).syncBody(body);
			}
			return Mono.error(errorResponse);
		});
	}
	*/
}
