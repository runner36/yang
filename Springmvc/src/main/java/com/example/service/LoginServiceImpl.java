package com.example.service;

import org.springframework.stereotype.Service;

import com.example.bean.User;

@Service
public class LoginServiceImpl implements LoginService {

	
	public boolean login(User u){
		return true;
	}
}
