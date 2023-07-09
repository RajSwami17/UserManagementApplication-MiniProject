package com.user.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserForm {
	
	private String fName;

	private String lName;

	private String email;

	private Long phoneno;

	private LocalDate dob;

	private String gender;

	private Integer cityId;

	private Integer stateId;

	private Integer countryId;

}
