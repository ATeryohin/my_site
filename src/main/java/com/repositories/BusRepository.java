package com.repositories;

import com.entity.Bus;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends CrudRepository<Bus, Integer> {

    Bus findOneByNumber(String number);

}
