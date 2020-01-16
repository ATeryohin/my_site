package com.services;

import com.entity.Driver;

import com.repositories.DriverRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService{

    private DriverRepository repository;
    private RequestValidator requestValidator;

    @Override
    public Driver addDriver(Driver driver) {
        requestValidator.driverRequestValidator(driver);

        return repository.save(driver);
    }

    @Override
    public List<Driver> findAll() {
        return (List<Driver>) repository.findAll();
    }

    @Override
    public Driver findOne(Integer id) {
        return repository.findOne(id);
    }

    @Autowired
    public void setRepository(DriverRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setRequestValidator(RequestValidator requestValidator) {
        this.requestValidator = requestValidator;
    }
}
