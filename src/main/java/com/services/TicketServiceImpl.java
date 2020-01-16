package com.services;

import com.entity.Ticket;

import com.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketRepository repository;

    @Override
    public Ticket findOne(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public List<Ticket> findAll() {
        return (List<Ticket>) repository.findAll();
    }

    @Autowired
    public void setRepository(TicketRepository repository) {
        this.repository = repository;
    }
}
