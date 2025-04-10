package com.example.Form_Management.config;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.example.Form_Management.master_user.masterUserEntity;
import com.example.Form_Management.master_user.masterUserRepository;
import com.example.Form_Management.security.userDetailsServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	masterUserRepository userRepo;
	
	@Autowired
	HttpSession session;

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	   
		 http
	        .authenticationProvider(authenticationProvider(passwordEncoder()))  
		 .csrf(Customizer.withDefaults()
				 
			    )
         .authorizeHttpRequests(auth -> auth
             .requestMatchers("/", "/login", "/assets/**","/WEB-INF/views/**").permitAll() 
             .requestMatchers("/js/**", "/css/**", "/images/**").permitAll()
             .requestMatchers(new CustomAjaxRequestMatcher()).hasAnyAuthority("User", "Admin")
             .requestMatchers("/profile", "/fill_forms", "/completed_forms").hasAnyAuthority("User","Admin")
             .anyRequest().hasAnyAuthority("Admin") 
         )
         .sessionManagement(session -> session
        	        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) 
        	    )
         .formLogin(form -> form
             .loginPage("/login") //Set login page
             .loginProcessingUrl("/perform_login") 
             .usernameParameter("username") 
             .passwordParameter("password") 
             .successHandler(authenticationSuccessHandler())
             .failureHandler(authenticationFailureHandler())
            // .defaultSuccessUrl("/master_form", true) 
             .failureUrl("/login?error=true") 
             .permitAll()
         )
         .logout(logout -> logout
             .logoutUrl("/logout")
             .logoutSuccessUrl("/")
             .invalidateHttpSession(true)
             .deleteCookies("JSESSIONID")
             .permitAll());


     return http.build();
    }
	
	@Bean
	public UserDetailsService userDetailsService() {
	    return new userDetailsServiceImpl(userRepo);
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
	    return (request, response, authentication) -> {
	        System.out.println("Authentication Successfully!");

	        try {
	            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	            System.out.println("Authenticated User: " + userDetails.getUsername());
	            System.out.println("Roles: " + userDetails.getAuthorities());

	            Optional<masterUserEntity> optionalUser = userRepo.findByUserEmail(userDetails.getUsername());

	            if (optionalUser.isPresent()) {
	                masterUserEntity loggedInUser = optionalUser.get();

	                HttpSession session = request.getSession();
	                session.setAttribute("loggedInUserId", loggedInUser.getUserId());
	                session.setAttribute("loggedInUserRole", loggedInUser.getUserRole());
	                session.setAttribute("loggedInUser", loggedInUser.getUsername());

	                System.out.println(" Session Created: " + session.getId());
	                System.out.println(" User ID: " + session.getAttribute("loggedInUserId"));
	                System.out.println(" User Role: " + session.getAttribute("loggedInUserRole"));
	                
	                if (loggedInUser.getUserRole().equals("Admin")) {
                        response.sendRedirect("/master_form"); 
                    } else if (loggedInUser.getUserRole().equals("User")) {
                        response.sendRedirect("/fill_forms"); 
                    } else {
                        response.sendRedirect("/login?error=invalid_role"); 
                    }
                } else {
                    System.out.println("User not found in database!");
                    response.sendRedirect("/login?error=true");
                }

	           // response.sendRedirect("/master_form");

	        } catch (Exception e) {
	            e.printStackTrace();  
	            System.out.println("Error in success handler: " + e.getMessage());
	        }
	    };
	}
	    
	    @Bean
	    public AuthenticationFailureHandler authenticationFailureHandler() {
	        return (request, response, exception) -> {
	            System.out.println("Login failed: " + exception.getMessage());
	            response.sendRedirect("/login?error=true");
	        };
	    }
	
	  @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(); 
	    }


	  @Bean
	  public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	      authProvider.setUserDetailsService(userDetailsService());
	      authProvider.setPasswordEncoder(passwordEncoder());
	      return authProvider;
	  }
	  
	  public class CustomAjaxRequestMatcher implements RequestMatcher {
			@Override
			public boolean matches(HttpServletRequest request) {
				String requestedWithHeader = request.getHeader("X-Requested-With");
				return "XMLHttpRequest".equals(requestedWithHeader);
			}
		}
}
