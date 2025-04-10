package com.example.Form_Management.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.Form_Management.master_user.masterUserEntity;
import com.example.Form_Management.master_user.masterUserRepository;

@Service
public class userDetailsServiceImpl implements UserDetailsService {
	private masterUserRepository userRepository;

	@Autowired
	public userDetailsServiceImpl(masterUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		Optional<masterUserEntity> optionalUser = userRepository.findByUserEmail(userEmail);
		
		 if (optionalUser.isEmpty()) {
		        System.out.println("User not found: " + userEmail);
		        throw new UsernameNotFoundException("User not found");
		    }
		 masterUserEntity user = optionalUser.get(); 
		System.out.println("user found:" + user.getUserEmail());
		System.out.println("password:"+user.getPassword());
		System.out.println("role:"+user.getUserRole());

		  UserDetails userDetails = User.builder()
		            .username(user.getUserEmail())
		            .password(user.getPassword())
		            .authorities(user.getUserRole()) // FIX: Ensuring proper ROLE_ prefix
		            .build();
		  
		   System.out.println("Granted Authorities: " + userDetails.getAuthorities());
		return userDetails;
	}

}
