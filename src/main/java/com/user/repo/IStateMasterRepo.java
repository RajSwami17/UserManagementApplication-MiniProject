package com.user.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entities.StateMaster;

public interface IStateMasterRepo extends JpaRepository<StateMaster, Integer> 
{
	//SELECT * FROM STATE_MASTER WHERE COUNTRY_ID=?;
	public List<StateMaster> findByCountryId(Integer countryId);
}
