package com.aysimasavas.ecommerce.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aysimasavas.ecommerce.userservice.entity.User;
import com.aysimasavas.ecommerce.userservice.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@SuppressWarnings("deprecation")
	@Override
	public User getUserById(int id) {
		return userRepository.getOne(id);
	}

	@Override
	public User getUserByName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public User saveUser(User user) {

		return userRepository.save(user);
	}
}