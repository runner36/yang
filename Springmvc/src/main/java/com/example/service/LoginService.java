package com.example.service;

import org.springframework.stereotype.Service;

import com.example.bean.User;

@Service
public interface LoginService {

	
	public boolean login(User u);
}
