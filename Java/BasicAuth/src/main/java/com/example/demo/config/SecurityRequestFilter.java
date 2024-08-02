package com.example.demo.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class SecurityRequestFilter.
 */
@Component
@Slf4j
public class SecurityRequestFilter extends OncePerRequestFilter {

	/**
	 * Do filter internal.
	 *
	 * @param request the request
	 * @param response the response
	 * @param filterChain the filter chain
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {

		final String headerValue = request.getHeader( HttpHeaders.AUTHORIZATION );
		if (null == headerValue || !headerValue.startsWith("Basic ")) {
			log.error("No Basic Auth Creds in AUTHORIZATION-Header sent");
			filterChain.doFilter( request, response ); 
			return; 
		}
		String username = null;
		String password = null;

		String base64Credentials = headerValue.substring("Basic ".length()).trim();

		// Decode the Base64-encoded credentials
		String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);

		// Split the credentials into username and password
		String[] values = credentials.split(":", 2);

		if (values.length == 2) {
			username = values[0];
			password = values[1];
		} else {
			log.error("Either userName or password is missing in AUTHORIZATION-Header !");
			filterChain.doFilter( request, response ); 
			return;
		}

		if(username.contentEquals("testUser") && password.contentEquals("Qwerty123")) {
			UserDetails userDetails = new User(username, "", new ArrayList<>());
			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(
							userDetails, null, Optional.ofNullable( userDetails ).map( UserDetails::getAuthorities ).orElse( List.of() ) );

			authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ) );
			SecurityContextHolder.getContext().setAuthentication( authentication );
			log.info("Verified the Basic-Auth Creds SuccessFully ! ");
		}

		filterChain.doFilter( request, response );

	}

}
