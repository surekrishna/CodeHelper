package com.krish.config;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
public class CorsConfiguration {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(CorsConfiguration.class);
	
	@Value("${webapp.domain}")
	private String webappDomain;
	
	@Value("${spring.profiles.active}")
	private String environment;

//  private final String ALLOWED_ORIGIN = "http://localhost:4200";
  private final Long MAX_AGE = 3600L;
  private List<String> ALLOWED_HEADERS = List.of("x-requested-with", "authorization", "Content-Type", "Authorization", "credential", "X-XSRF-TOKEN", "X-AUTH-TOKEN");
  private List<HttpMethod> ALLOWED_METHODS = List.of(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST, HttpMethod.OPTIONS, HttpMethod.DELETE);
   
  @Bean
  public WebFilter corsFilter() {
    return (ServerWebExchange ctx, WebFilterChain chain) -> {
      ServerHttpRequest request = ctx.getRequest();
      if (CorsUtils.isCorsRequest(request)) {
        ServerHttpResponse response = ctx.getResponse();
        HttpHeaders headers = response.getHeaders();
        
        headers.setAccessControlAllowHeaders(ALLOWED_HEADERS);
        headers.setAccessControlAllowMethods(ALLOWED_METHODS);
        LOGGER.info("Domain name: "+ webappDomain);
        
        // TODO: need to remove this before live
//        if (environment.equalsIgnoreCase("local") || environment.equalsIgnoreCase("dev")) {
//        	String origin = request.getHeaders().getOrigin();
//        	headers.setAccessControlAllowOrigin(origin);
//        } else {
//        	headers.setAccessControlAllowOrigin(webappDomain);
//        }
        headers.setAccessControlAllowOrigin(webappDomain);
        headers.setAccessControlAllowCredentials(true);
        headers.setAccessControlMaxAge(MAX_AGE);
				
        if (request.getMethod() == HttpMethod.OPTIONS) {
          response.setStatusCode(HttpStatus.OK);
          return Mono.empty();
        }
      }
      return chain.filter(ctx);
    };
  }
}

