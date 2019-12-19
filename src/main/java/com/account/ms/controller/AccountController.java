package com.account.ms.controller;

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
import com.account.ms.models.SavingAccount;
import com.account.ms.models.services.AccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountController {
	
	@Autowired
	private AccountService service;
	
	public Mono<ServerResponse> listar(ServerRequest request){
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(service.findAll(), Account.class);
		 
	}
	
	public Mono<ServerResponse> show(ServerRequest request){
		String id = request.pathVariable("id");
		return errorHandler(
						service.findById(id).flatMap(p-> ServerResponse.ok()
						.contentType(APPLICATION_JSON_UTF8)
						.syncBody(p))
						.switchIfEmpty(ServerResponse.notFound().build())
				
				);
				
	}
	
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

}
