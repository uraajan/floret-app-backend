package com.floret.floretappbackend.basic.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthUserDetailsService implements UserDetailsService {

	static List<BasicAuthUserDetails> inMemoryUserList = new ArrayList<BasicAuthUserDetails>();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<BasicAuthUserDetails> userDetails = inMemoryUserList.stream().filter(user -> user.getUsername().equals(username)).findFirst();

		if (!userDetails.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}

		return userDetails.get();
	}

	public static void addUser(BasicAuthUserDetails basicAuthUserDetails) {
		inMemoryUserList.add(basicAuthUserDetails);
		System.out.println("User added to user details: " + basicAuthUserDetails.getUsername());
	}

}
