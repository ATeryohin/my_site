package com.repositories;

import com.entity.Voyage;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface VoyageRepository extends CrudRepository<Voyage, Integer> {

    Voyage findOneByNumber(String number);

}
