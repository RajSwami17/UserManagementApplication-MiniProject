package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.UserManagementService;

@RestController
public class ForgotPasswordRestController {
	
	@Autowired
	private UserManagementService service;

	@GetMapping("/forgot/{email}")
	public String forgotPassword(@PathVariable("email") String email) {
		System.out.println("ForgotPasswordRestController.forgotPassword()");
		return service.forgotPwd(email);
	}

}
