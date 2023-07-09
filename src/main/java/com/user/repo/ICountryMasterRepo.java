package com.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entities.CountryMaster;

public interface ICountryMasterRepo extends JpaRepository<CountryMaster, Integer> {

}
