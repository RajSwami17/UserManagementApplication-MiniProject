package com.user.service;

import java.util.Map;

import com.user.bindings.LoginForm;
import com.user.bindings.UnlockForm;
import com.user.bindings.UserForm;

public interface UserManagementService 
{
	
	//Login Form Functionality Method
	public String login(LoginForm loginForm);
	
	//Registration Functionality Methods
	public String emailCheck(String emailId);
	
	//it will load the country
	public Map<Integer,String> loadCountries();
	
	//it will load the states based on countryId
	public Map<Integer,String> loadStates(Integer countryId);
	
	//it will load the city based on stateId
	public Map<Integer,String> loadCities (Integer stateId);
	
	//it will save the user details
	public String registerUser(UserForm userForm);
	
	//Unlock Account Functionality Method
	public String unlockAccount (UnlockForm unlockForm);
	
	//forgot password functionality method
	public String forgotPwd(String emailId);
		
}
