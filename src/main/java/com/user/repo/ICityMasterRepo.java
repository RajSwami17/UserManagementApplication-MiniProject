package com.user.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entities.CityMaster;

public interface ICityMasterRepo extends JpaRepository<CityMaster,Integer>
{
	//SELECT * FORM CITY_MASTER WHERE STATE_ID = ?;
	public List<CityMaster> findByStateId(Integer stateId); 
}
