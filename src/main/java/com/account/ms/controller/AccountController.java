package com.account.ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.account.ms.mapper.AccountMapper;
import com.account.ms.models.Account;
import com.account.ms.models.SavingAccount;
import com.account.ms.models.services.AccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RestController
public class AccountController {
	
	@Autowired
	private AccountService service;
	
	@Autowired
    private WebClient client;
	
	@Autowired
    private WebClient.Builder webClientBuilder;
	
	public Mono<ServerResponse> listar(ServerRequest request){
		return ServerResponse.ok().contentType(APPLICATION_JSON)
				.body(service.findAll(), Account.class);
	}

	public Mono<ServerResponse> ver(ServerRequest request){
		String id = request.pathVariable("id");
		return service.findById(id).flatMap(p -> ServerResponse.ok()
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(p))
				.switchIfEmpty(ServerResponse.notFound().build())
				.onErrorResume(error -> {
					WebClientResponseException responseError = (WebClientResponseException) error;
					if(responseError.getStatusCode() == HttpStatus.NOT_FOUND) {
						return ServerResponse.notFound().build();
					}
					return Mono.error(responseError);
				});
	}
	
	@GetMapping("/accounts/{id}")
	public Mono<Account> showAccount(@PathVariable("id") String id){
		
		/*Mono<Account> accountMono = client.get()
		   .uri("http://localhost:8070/accounts/{id}",id)
		   .retrieve()
		   .bodyToMono(Account.class);*/
		
		return service.findById(id);
	}
	
	
	@GetMapping("/{id}/savingAccounts")
	public Mono<Account> findByIdWithSavingAccounts(@PathVariable("id") String id){
		
		Flux<SavingAccount> savingaccounts = webClientBuilder.build().get().uri("http://localhost:8080/account/{account}", id).retrieve().bodyToFlux(SavingAccount.class);		
		return savingaccounts
				.collectList()
				.map(a -> new Account())
				.mergeWith(service.findById(id))
				.collectList()
				.map(AccountMapper::map);
		
	}
	
	
	
	public Mono<Account> insertBankAccount (@RequestBody Account data){
		return client.post().uri("/api/savingaccount").syncBody(data)
				.retrieve().bodyToMono(Account.class);	
	}
	
    @PostMapping("/insert")
	public Mono<SavingAccount> insertBankSavingAccount (@RequestBody SavingAccount savingAccount){
		Account account = new Account() ;
		account.setNumberAccount(savingAccount.getAccountNumber());
		insertBankAccount(account).subscribe();
		return service.saveAccount(savingAccount);
	}
    
    @PutMapping("/updateAccount/{id}")
    public Mono<Account> insertDeposit(@PathVariable("id") final String id, @RequestBody final Account account){
  
        BigDecimal balance = account.getCurrentBalance();
        BigDecimal deposit = account.getDeposit();
        //BigDecimal withdraw = new BigDecimal("50.00");
    	deposit = balance.add(deposit);
    	//withdraw = balance.subtract(withdraw);
    	balance = deposit;
    	account.setCurrentBalance(balance);
    	
    	return service.update(account, id);
 
    }
    
    @PutMapping("/updateAccount/{id}")
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
    
    
	
	@GetMapping("/person/{person}")
	public Flux<Account> findByPerson(@PathVariable("person") String id) {
		return service.findByPersonId(id);
	}
	
	
	

}
