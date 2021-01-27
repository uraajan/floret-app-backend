package com.floret.floretappbackend.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.floret.floretappbackend.basic.auth.AuthenticationCache;
import com.floret.floretappbackend.basic.auth.BasicAuthUserDetailsService;

@Component
public class BasicAuthRequestFilter extends OncePerRequestFilter {

	@Autowired
	private BasicAuthUserDetailsService basicAuthUserDetailsService;

	@Autowired
	private AuthenticationCache authenticationCache;

	@Value("Authorization")
	private String tokenHeader;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		System.out.println("Token header: " + this.tokenHeader);
		final String requestTokenHeader = request.getHeader(this.tokenHeader);
		System.out.println("Token header value: " + requestTokenHeader);

		String basicAuthToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Basic")) {

			// Remove "Basic " from request token header
			basicAuthToken = requestTokenHeader.substring(5);

			if (authenticationCache.getUserDetailsFromTokenCache(basicAuthToken) != null) {

				String username = authenticationCache.getUserDetailsFromTokenCache(basicAuthToken).getUsername();
				UserDetails userDetails = basicAuthUserDetailsService.loadUserByUsername(username);

				if (authenticationCache.validateToken(basicAuthToken)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}

		filterChain.doFilter(request, response);
	}

}
