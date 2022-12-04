package com.aysimasavas.ecommerce.userservice.service;

import java.util.List;

import com.aysimasavas.ecommerce.userservice.entity.User;

public interface UserService {

	List<User> getAllUsers();

	User getUserById(int id);

	User getUserByName(String userName);

	User saveUser(User user);

}
