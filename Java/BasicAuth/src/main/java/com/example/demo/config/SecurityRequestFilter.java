package com.example.demo.config;
import java.io.IOException;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

public class SecurityRequestFilter extends OncePerRequestFilter {

	private UserDetailsService userDetailsService;

	private AuthenticationManager authenticationManager;

	public SecurityRequestFilter(UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
		super();
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Basic ")) {
			String[] credentials = extractCredentialsFromHttpHeader(header);
			String username = credentials[0];
			String password = credentials[1];

			try {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (AuthenticationException e) {
				SecurityContextHolder.clearContext();
			}
		} else {
			SecurityContextHolder.getContext().setAuthentication(null);
		}

		filterChain.doFilter(request, response);
	}

	private String[] extractCredentialsFromHttpHeader(String header) {
		String base64Credentials = header.substring("Basic ".length()).trim();
		String credentials = new String(Base64.getDecoder().decode(base64Credentials));
		return credentials.split(":", 2);
	}
}
