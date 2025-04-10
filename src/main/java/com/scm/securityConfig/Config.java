package com.scm.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.FormLoginBeanDefinitionParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class Config {

	// user create and login with java code in memory Service

//	@Bean 
//	 UserDetailsService userDetailsService(PasswordEncoder passwordEncoder)
//	{
//		UserDetails user1 = User.builder()
//				            .username("User")
//				            .password(passwordEncoder.encode("User"))
//				            .roles("ADMIN")
//				            .build();
//		UserDetails user2 = User.builder()
//				            .username("Admin")
//				            .password(passwordEncoder.encode("Admin123"))
//				            .build();
//		return new  InMemoryUserDetailsManager(user1,user2);
//		
//	}
//	

	@Autowired
	private CustomSecurityClassService userDetailsService;
	
	@Autowired
	private OauthSuccessHandler oauthSuccessHandler;

	@Bean
	private SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		security.authorizeHttpRequests(
				request -> request.requestMatchers("/user/**").authenticated().anyRequest().permitAll())
				.formLogin(formLogin -> {
					formLogin.loginPage("/login");
					formLogin.loginProcessingUrl("/authenticate");
					formLogin.successForwardUrl("/user/profile");
					//formLogin.failureForwardUrl("/login?error=true");
					formLogin.usernameParameter("email");
					formLogin.passwordParameter("password");
				});
		            security.csrf(csrf -> csrf.disable());
		            security.logout(logoutForm -> {
			        logoutForm.logoutUrl("/logout");
			        logoutForm.logoutSuccessUrl("/login?logout=true");
		});
		            security.oauth2Login(oauth2->{
		            	oauth2.loginPage("/login");
		            	oauth2.successHandler(oauthSuccessHandler);
		            	});
		            return security.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;
	}

}
