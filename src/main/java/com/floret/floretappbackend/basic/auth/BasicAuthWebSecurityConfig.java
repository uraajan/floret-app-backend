package com.floret.floretappbackend.basic.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.floret.floretappbackend.filter.BasicAuthRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BasicAuthWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BasicAuthRequestFilter basicAuthRequestFilter;

	@Autowired
	private BasicAuthUnauthorizedResponseAuthenticationEntryPoint authenticationEntryPoint;
	
	@Value("/register")
	private String registerPath;
	
	@Value("/authenticate")
	private String authenticationPath;
	
	@Value("/admin/authenticate")
	private String adminAuthenticationPath;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.cors().and()
			.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().anyRequest().authenticated();

		httpSecurity
			.addFilterBefore(basicAuthRequestFilter, UsernamePasswordAuthenticationFilter.class);

		httpSecurity
			.headers()
			.frameOptions()
			.sameOrigin()
			.cacheControl();
	}
	
	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity
			.ignoring()
			// Exception to allow "/register" request without auth token
			.antMatchers(HttpMethod.POST, registerPath)
			// Exception to allow "/authenticate" request without auth token
			.antMatchers(HttpMethod.POST, authenticationPath)
			.antMatchers(HttpMethod.POST, adminAuthenticationPath)
			.antMatchers(HttpMethod.OPTIONS, "/**").and()
			.ignoring()
			.antMatchers(HttpMethod.GET, "/");
	}

}
