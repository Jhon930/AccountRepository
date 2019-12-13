package com.account.ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.http.MediaType.*;

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
	

	public Mono<Account> insertBankAccount (@RequestBody Account data){
		return client.post().uri("/api/client").syncBody(data)
				.retrieve().bodyToMono(Account.class);	
	}
	
	@PostMapping("/insert")
	public Mono<SavingAccount> insertBankSavingAccount (@RequestBody SavingAccount savingAccount){
		Account account = new Account() ;
		account.setNumberAccount(savingAccount.getAccountNumber());
		insertBankAccount(account).subscribe();
		return service.saveAccount(savingAccount);
	}
	
	@GetMapping("/person/{person}")
	public Flux<Account> findByPerson(@PathVariable("person") String id) {
		return service.findByPersonId(id);
	}
	
	
	/*public Mono<ServerResponse> eliminar(ServerRequest request){
		String id = request.pathVariable("id");
		return service.delete(id).then(ServerResponse.noContent().build())
				.onErrorResume(error -> {
					WebClientResponseException responseError = (WebClientResponseException) error;
					if(responseError.getStatusCode() == HttpStatus.NOT_FOUND) {
						return ServerResponse.notFound().build();
					}
					return Mono.error(responseError);
				});
	}*/
	

}
