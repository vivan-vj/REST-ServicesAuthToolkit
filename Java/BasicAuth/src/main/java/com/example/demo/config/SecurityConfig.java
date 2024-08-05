package com.example.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	private SecurityRequestFilter getSecurityRequestFilter() {
		try {
			return new SecurityRequestFilter(userDetailsServiceBean(), authenticationManager());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.addFilterBefore(getSecurityRequestFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public UserDetailsService userDetailsServiceBean() {
		return new InMemoryUserDetailsManager(
				User.withUsername("testUser")
				.password("{noop}asdf123")
				.roles("USER")
				.build()
				);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService user= userDetailsServiceBean();
		auth.inMemoryAuthentication().withUser(user.loadUserByUsername("testUser"));
	}

}
