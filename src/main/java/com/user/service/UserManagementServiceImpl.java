package com.user.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.bindings.LoginForm;
import com.user.bindings.UnlockForm;
import com.user.bindings.UserForm;
import com.user.entities.CityMaster;
import com.user.entities.CountryMaster;
import com.user.entities.StateMaster;
import com.user.entities.UserAccount;
import com.user.repo.ICityMasterRepo;
import com.user.repo.ICountryMasterRepo;
import com.user.repo.IStateMasterRepo;
import com.user.repo.IUserAccountRepo;
import com.user.utils.EmailUtils;

@Service
public class UserManagementServiceImpl implements UserManagementService
{
	@Autowired
	private IUserAccountRepo userRepo;
	
	@Autowired
	private ICityMasterRepo cityRepo;
	
	@Autowired
	private ICountryMasterRepo countryRepo;
	
	@Autowired
	private IStateMasterRepo stateRepo;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String login(LoginForm loginForm) 
	{
		System.out.println("UserManagementServiceImpl.login()");
		
		UserAccount entity = userRepo.findByEmailAndPazzword(loginForm.getEmail(), loginForm.getPwd());
		
		if(entity==null)
		{
			return "Invalid Credentials";
			
		}
		
		if(entity!=null && entity.getAccStatus().equals("LOCKED"))
		{
			return "Your Account is Locked";
		}
		
		return "Login Successful,Welcome to User Management App..!";
	}
	//it is used to verify with the given mailId do we have a record or not.
	@Override
	public String emailCheck(String emailId)  
	{
		System.out.println("UserManagementServiceImpl.emailCheck()");
		
		UserAccount entity = userRepo.findByEmail(emailId);
		
		if(entity==null)
		{
			return "Unique Mail Id";
		}
		return "Mail Already Exist";
	}
 
	@Override
	public Map<Integer, String> loadCountries() 
	{
		System.out.println("UserManagementServiceImpl.getCountry()");
	
		List<CountryMaster> countries = countryRepo.findAll();//it will give list of all countries 
		
		Map<Integer,String> countryMap = new HashMap<>();//here we create empty map object.
		
		for(CountryMaster country : countries)//here we convert list of countries into map
		{
			countryMap.put(country.getCountryId(),country.getCountryName());//here we store country id and country name in map
		}
		
		return countryMap;//after storing all values finally we return the map object.
	}

	@Override
	public Map<Integer, String> loadStates(Integer countryId) 
	{
		System.out.println("UserManagementServiceImpl.getState()");
		 
		List<StateMaster> states =stateRepo.findByCountryId(countryId);
		
		Map<Integer,String> statesMap = new HashMap<>();
		
		for(StateMaster state:states)
		{
			statesMap.put(state.getStateId(), state.getStateName());
		}
		
		return statesMap;
	}

	@Override
	public Map<Integer, String> loadCities(Integer stateId) 
	{
		System.out.println("UserManagementServiceImpl.getCity()");
		
		List<CityMaster> cities = cityRepo.findByStateId(stateId);
		
		Map<Integer,String> citiesMap = new HashMap<>();
		
		for(CityMaster city:cities)
		{
			citiesMap.put(city.getCityId(),city.getCityName());
		}
		
		return citiesMap;
	}

	@Override
	public String registerUser(UserForm userForm) 
	{
		System.out.println("UserManagementServiceImpl.registerUser()");
		
		UserAccount entity =  new UserAccount();
		
		BeanUtils.copyProperties(userForm, entity);//used to copy the data form userForm object to entity object.
		
		entity.setAccStatus("LOCKED");
		
		//Generate Random password
		entity.setPazzword(generateRandomPwd());
		
		UserAccount savedEntity = userRepo.save(entity);
		
		//logic to send mail
		String email = userForm.getEmail();
		
		String subject = "User RegiStration - User Management App";
		
		String fileName = "UNLOCK-ACC-EMAIL-BODY-TEMPLATE.txt";
		
		String body = readMailBodyContent(fileName,entity);
		
		boolean isSent = emailUtils.sendEmail(email, subject, body);
		
		if(savedEntity.getUserId()!=null && isSent) 
		{
			return "User Register Successfully and Mail Sent to User Mail..";
		}
		
		return "Problem with Email Sent";
	}

	@Override
	public String unlockAccount(UnlockForm unlockForm) 
	{
		System.out.println("UserManagementServiceImpl.accountUnlock()");
		
		if(!(unlockForm.getNewPwd().equals(unlockForm.getConfirmNewPwd())))
		{
			return "Entered Password and Confirm Password Should Be Same..";
		}
		
		UserAccount entity = userRepo.findByEmailAndPazzword(unlockForm.getEmail(), unlockForm.getTempPwd());
		
		if(entity==null)
		{
			return "Incorrect Temporary Password";
		}
		
		entity.setPazzword(unlockForm.getNewPwd());
		entity.setAccStatus("UNLOCKED");
		
		userRepo.save(entity);//it will update the old password value with new password value
		
		return "Account Unlocked";
	}

	@Override
	public String forgotPwd(String emailId) 
	{
		System.out.println("UserManagementServiceImpl.forgotPwd()");
		
		UserAccount entity = userRepo.findByEmail(emailId);
		
		if(entity==null)
		{
			return "Invalid Email Id";
		}
		//if email available password will be send to user mail.
		// Email Sending Logic
		String fileName = "RECOVER-PASSWORD-EMAIL-BODY-TEMPLATE.txt";
		
		String body = readMailBodyContent(fileName,entity);
		
		String subject = "Recover Forgotton Password - User Management App";
		
		boolean isSent = emailUtils.sendEmail(emailId, subject, body);
		
		if(isSent)
		{
			return "Password Sent To Registered Email..";
		}
		
		return "Something Went Wrong";
	}
	
	private String generateRandomPwd() 
	{
		System.out.println("UserManagementServiceImpl.generateRandomPwd()");
	    int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 6;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    return generatedString;
	}
	
	private String readMailBodyContent(String fileName,UserAccount entity)
	{
		System.out.println("UserManagementServiceImpl.readMailBodyContent()");
		
		String mailBody = null;
		try
		{
			//StringBuilder sb = new StringBuilder();
			StringBuffer sb = new StringBuffer();
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();//reading first line data.
			
			while(line!=null)
			{
				sb.append(line);//appending line data to buffer object.
				line = br.readLine();//reading next line data.
			}
			
			mailBody = sb.toString();
			mailBody = mailBody.replace("{FNAME}",entity.getFName());
			mailBody = mailBody.replace("{LNAME}",entity.getLName());
			mailBody = mailBody.replace("{TEMP-PWD}",entity.getPazzword());
			mailBody = mailBody.replace("{EMAIL}",entity.getEmail());
			mailBody = mailBody.replace("{PWD}",entity.getPazzword());
			
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return mailBody;
	}
}
