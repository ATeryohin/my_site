package com.repositories;

import com.entity.Driver;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends CrudRepository<Driver, Integer>{

    Driver findOneByLicense(String license);

}
