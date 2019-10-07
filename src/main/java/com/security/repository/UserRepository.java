package com.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoException;
import com.security.model.User;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	public User findByUserName(String name) throws MongoException;
	
}