package com.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entities.UserAccount;

public interface IUserAccountRepo extends JpaRepository<UserAccount,Integer>
{
	//SELECT * FROM USER_ACCOUNT WHERE USER_EMAIL=? AND USER_PWD=?;
	public UserAccount findByEmailAndPazzword(String email,String pwd);
	
	//SELECT * FROM USER_ACCOUNT WHERE USER_EMAIL=?;
	public UserAccount findByEmail(String emailId);
	
 
}
