package com.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.bindings.UserForm;
import com.user.service.UserManagementService;

@RestController
public class UserRegistrationRestController 
{
	@Autowired
	private UserManagementService service;
	
	@GetMapping("/email/{emailId}")
	public String emailCheck(@PathVariable("emailId") String emailId)
	{
		System.out.println("UserRegistrationRestController.emailCheck()");
		return service.emailCheck(emailId);
	}
	
	@GetMapping("/countries")
	public Map<Integer,String> getCountries() 
	{
		System.out.println("UserRegistrationRestController.loadCountries()");
		return service.loadCountries();
	}
	
	@GetMapping("/states/{countryId}")
	public Map<Integer,String> getStates(@PathVariable("countryId")Integer countryId)
	{
		System.out.println("UserRegistrationRestController.loadStates()");
		return service.loadStates(countryId);
	}
	
	@GetMapping("/cities/{stateId}")
	public Map<Integer,String> getCities(@PathVariable("stateId")Integer stateId)
	{
		System.out.println("UserRegistrationRestController.loadCities()");
		return service.loadCities(stateId);
	}
	
	@PostMapping("/user")
	public String userReg(@RequestBody UserForm userForm)
	{
		System.out.println("UserRegistrationRestController.userRegistration()"); 
		return service.registerUser(userForm);
	}
}
