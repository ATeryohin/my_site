package com.services;

import com.entity.Driver;

import java.util.List;

public interface DriverService {

    Driver addDriver(Driver driver);
    Driver findOne(Integer id);
    List<Driver> findAll();

}
