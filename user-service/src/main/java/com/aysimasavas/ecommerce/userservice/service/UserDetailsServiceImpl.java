package com.aysimasavas.ecommerce.userservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aysimasavas.ecommerce.userservice.entity.User;
import com.aysimasavas.ecommerce.userservice.repository.UserRepository;
import com.aysimasavas.ecommerce.userservice.security.JwtUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		return JwtUserDetails.create(user);
	}

	public UserDetails loadUserById(int id) {
		User user = userRepository.findById(id).get();
		return JwtUserDetails.create(user);
	}

}
