package com.services;

import com.entity.Bus;

import com.repositories.BusRepository;

import com.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusServiceImpl implements BusService {

    private BusRepository repository;
    private DriverRepository driverRepository;
    private RequestValidator requestValidator;

    @Override
    public Bus addBus(Bus bus) {
        requestValidator.busRequestValidator(bus);

        return repository.save(bus);
    }

    @Override
    public Bus changeDriverOnBus(Integer busId, Integer driverId) {

        requestValidator.busIdDriverIdValidator(busId, driverId);

        Bus bus = repository.findOne(busId);
        bus.setDriver(driverRepository.findOne(driverId));
        return repository.save(bus);
    }

    @Override
    public Bus findOne(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public List<Bus> findAll() {
        return (List<Bus>) repository.findAll();
    }

    @Autowired
    public void setRepository(BusRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setDriverRepository(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Autowired
    public void setRequestValidator(RequestValidator requestValidator) {
        this.requestValidator = requestValidator;
    }
}
