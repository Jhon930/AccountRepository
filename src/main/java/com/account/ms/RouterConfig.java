package com.account.ms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.account.ms.controller.AccountController;

//@Configuration
public class RouterConfig {
	
	/*@Bean
	public RouterFunction<ServerResponse> rutas(AccountController controller){
		return route(GET("/api/account"), controller::findAll)
				.andRoute(GET("/api/account/{id}"), controller::show)
		 			.andRoute(GET("/api/account/person/{person}"), controller::findByPerson);
	}*/

}
