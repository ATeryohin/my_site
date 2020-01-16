package com.services;

import com.entity.Bus;

import java.util.List;

public interface BusService {

    Bus addBus(Bus bus);
    Bus changeDriverOnBus(Integer busId, Integer driverId);
    Bus findOne(Integer id);
    List<Bus> findAll();

}
