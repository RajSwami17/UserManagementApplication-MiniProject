package com.user.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name="USER_ACCOUNT")
@Data
public class UserAccount 
{
	@Id
	@GeneratedValue
	@Column(name="USER_ID")
	private Integer userId;
	
	@Column(name="FIRST_NAME")
	private String fName;
	
	@Column(name="LAST_NAME")
	private String lName;
	
	@Column(name="USER_EMAIL",unique=true)
	private String email;
	
	@Column(name="USER_PWD")
	private String pazzword;
	
	@Column(name="USER_MOBILE")
	private long phoneno;
	
	@Column(name="DOB")
	private LocalDate dob;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="CITY_ID")
	private int cityId;
	
	@Column(name="STATE_ID")
	private int stateId;
	
	@Column(name="COUNTRY_ID")
	private int countryId;
	
	@Column(name="ACC_STATUS")
	private String 	accStatus;
	
	@Column(name="CREATED_DATE",updatable=false)
	@CreationTimestamp
	private LocalDate createdDate;
	
	@Column(name="UPDATED_DATE",insertable=false)
	@UpdateTimestamp
	private LocalDate updatedDate;
	
}
