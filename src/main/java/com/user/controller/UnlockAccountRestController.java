package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.bindings.UnlockForm;
import com.user.service.UserManagementService;

@RestController
public class UnlockAccountRestController 
{
	@Autowired
	private UserManagementService service;
	
	@PostMapping("/unlock")
	public String accountUnlock(@RequestBody UnlockForm unlockForm)
	{
		System.out.println("UnlockAccountRestController.unlockAccount()");
		return service.unlockAccount(unlockForm);
	}
}
